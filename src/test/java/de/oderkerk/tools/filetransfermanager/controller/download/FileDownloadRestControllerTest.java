package de.oderkerk.tools.filetransfermanager.controller.download;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import de.oderkerk.tools.filetransfermanager.controller.CorrelationHeaderFilter;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileDownloadRestControllerTest {

	MockFilterChain mockChain;
	MockHttpServletRequest req;
	MockHttpServletResponse rsp;
	CorrelationHeaderFilter correlationHeaderFilter;
	@Autowired
	FileDownloadRestController fileDownloadRestController;

	@Before
	public void setUp() throws Exception {
		mockChain = new MockFilterChain();
		req = new MockHttpServletRequest("/downloadFile/xxxx.txt", "/");
		rsp = new MockHttpServletResponse();
		correlationHeaderFilter = new CorrelationHeaderFilter();
		correlationHeaderFilter.init(new MockFilterConfig());
		correlationHeaderFilter.doFilter(req, rsp, new MockFilterChain());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDownloadFileError() {
		try {
			req = new MockHttpServletRequest("/downloadFile/xxxx.txt", "/");
			fileDownloadRestController.downloadFile("xxxx.txt", req);
		} catch (CustomFileNotFoundException ex) {
			assertEquals("File not found xxxx.txt", ex.getMessage());
		} catch (Exception ex) {
			fail(ex.toString());
		}
	}

	@Test
	public void testDownloadFile() {
		try {
			File f = new File("target/download/downloadtest.txt");
			if (!f.exists())
				f.createNewFile();
			req = new MockHttpServletRequest("/downloadFile/downloadtest.txt", "/");
			ResponseEntity<Resource> result = fileDownloadRestController.downloadFile("downloadtest.txt", req);
			assertEquals(HttpStatus.OK, result.getStatusCode());
		} catch (Exception ex) {
			fail(ex.toString());
		}
	}

}
