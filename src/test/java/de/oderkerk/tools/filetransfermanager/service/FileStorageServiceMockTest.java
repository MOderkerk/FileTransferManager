package de.oderkerk.tools.filetransfermanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import de.oderkerk.tools.filetransfermanager.config.FileSystemProperties;
import de.oderkerk.tools.filetransfermanager.controller.CorrelationHeaderFilter;
import de.oderkerk.tools.filetransfermanager.controller.download.CustomFileNotFoundException;

@RunWith(SpringRunner.class)
public class FileStorageServiceMockTest {

	private FileStorageService fileStorageService;

	private FileSystemProperties fileSystemProperties;
	MockFilterChain mockChain;
	MockHttpServletRequest req;
	MockHttpServletResponse rsp;
	CorrelationHeaderFilter correlationHeaderFilter;
	MockMultipartFile file;

	@Before
	public void setUp() throws Exception {
		file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
		mockChain = new MockFilterChain();
		req = new MockHttpServletRequest("/uploadfile", "/");
		rsp = new MockHttpServletResponse();
		correlationHeaderFilter = new CorrelationHeaderFilter();
		correlationHeaderFilter.init(new MockFilterConfig());
		correlationHeaderFilter.doFilter(req, rsp, new MockFilterChain());

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFSCreationException() throws Exception {
		try {
			fileSystemProperties = new FileSystemProperties();
			fileSystemProperties.setDownloadfolder("target/&&:&&&");
			fileSystemProperties.setUploadfolder("target/&&&&:&");
			fileSystemProperties.setReplaceExistingFile(true);
			fileStorageService = new FileStorageService(fileSystemProperties);
		} catch (FileStorageException ex) {
			assertEquals(FileStorageException.class, ex.getClass());
		}
	}

	@Test
	public void testStoreFileException() throws Exception {
		try {

			fileSystemProperties = new FileSystemProperties();
			fileSystemProperties.setDownloadfolder("target/dl");
			fileSystemProperties.setUploadfolder("target/up");
			fileSystemProperties.setReplaceExistingFile(true);
			fileStorageService = Mockito.mock(FileStorageService.class);
			doThrow(IOException.class).when(fileStorageService).copyFile(ArgumentMatchers.any(),
					ArgumentMatchers.any());
			fileStorageService.copyFile(file, ":");
			fail("no exception occured");
		} catch (IOException ex) {
			assertEquals(IOException.class, ex.getClass());
		}
	}

	@Test
	public void testDownloadFileException() throws Exception {
		try {

			fileSystemProperties = new FileSystemProperties();
			fileSystemProperties.setDownloadfolder("target/dl");
			fileSystemProperties.setUploadfolder("target/up");
			fileSystemProperties.setReplaceExistingFile(true);
			FileStorageService fileStorageService2 = new FileStorageService(fileSystemProperties);

			fileStorageService2.downloadFileAsResource(":");
			fail("no exception occured");
		} catch (CustomFileNotFoundException ex) {
			assertEquals(CustomFileNotFoundException.class, ex.getClass());
		}
	}

}