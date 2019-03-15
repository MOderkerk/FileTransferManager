package de.oderkerk.tools.filetransfermanager.controller.upload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import de.oderkerk.tools.filetransfermanager.controller.CorrelationHeaderFilter;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileUploadRestControllerTest {

	MockFilterChain mockChain;
	MockHttpServletRequest req;
	MockHttpServletResponse rsp;
	CorrelationHeaderFilter correlationHeaderFilter;
	MockMultipartFile file;

	@Autowired
	FileUploadRestController fileUploadRestController;

	@Before
	public void setUp() throws Exception {
		mockChain = new MockFilterChain();
		req = new MockHttpServletRequest("/uploadFile", "/");
		rsp = new MockHttpServletResponse();
		correlationHeaderFilter = new CorrelationHeaderFilter();
		correlationHeaderFilter.init(new MockFilterConfig());
		correlationHeaderFilter.doFilter(req, rsp, new MockFilterChain());
		file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUploadSingleFile() {
		try {
			FileUploadResponse resp = fileUploadRestController.uploadSingleFile(file);
			assertNotNull(resp);
			assertEquals(true, resp.isUploadSuccessfull());
			assertEquals("http://localhost/downloadFile/filename.txt", resp.getFileDownloadUri());
			assertEquals("filename.txt", resp.getFileName());
			assertEquals("text/plain", resp.getFileType());
			assertEquals(8l, resp.getSize());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

}
