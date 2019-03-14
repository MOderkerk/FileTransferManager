package de.oderkerk.tools.filetransfermanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import de.oderkerk.tools.filetransfermanager.controller.CorrelationHeaderFilter;
import de.oderkerk.tools.filetransfermanager.controller.download.CustomFileNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileStorageServiceTest {

	@Autowired(required = false)
	private FileStorageService fileStorageService;

	MockMultipartFile file;
	MockMultipartFile fileError;
	MockFilterChain mockChain;
	MockHttpServletRequest req;
	MockHttpServletResponse rsp;
	CorrelationHeaderFilter correlationHeaderFilter;

	@Before
	public void setUp() throws Exception {
		file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
		fileError = new MockMultipartFile("data", "../filename.txt", "text/plain", "some xml".getBytes());
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
	public void testFileStorageService() {
		assertNotNull(fileStorageService);
	}

	@Test
	public void testStoreFile() {
		try {
			String result = fileStorageService.storeFile(file);
			assertEquals("filename.txt", result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testStoreFileException() {
		try {
			fileStorageService.storeFile(fileError);
			fail("Exception not thrown");
		} catch (Exception e) {
			assertEquals(FileStorageException.class, e.getClass());
			assertEquals("Filename contains invalid path sequence .. Due to security reasons this is not allowed",
					e.getMessage());
		}
	}

	@Test
	public void testDownloadFileAsResourceError() {
		try {
			fileStorageService.downloadFileAsResource("Test.txt");

		} catch (Exception e) {
			assertEquals(CustomFileNotFoundException.class, e.getClass());
		}
	}

	@Test
	public void testDownloadFileAsResource() {
		try {
			File f = new File("target/download/Test2.txt");
			if (!f.canExecute())
				f.createNewFile();
			Resource r = fileStorageService.downloadFileAsResource("Test2.txt");
			assertEquals("Test2.txt", r.getFilename());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
