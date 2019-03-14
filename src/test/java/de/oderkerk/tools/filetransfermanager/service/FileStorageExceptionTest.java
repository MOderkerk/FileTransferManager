package de.oderkerk.tools.filetransfermanager.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import de.oderkerk.tools.filetransfermanager.controller.CorrelationHeaderFilter;

@SpringBootTest
public class FileStorageExceptionTest {
	MockFilterChain mockChain;
	MockHttpServletRequest req;
	MockHttpServletResponse rsp;
	CorrelationHeaderFilter correlationHeaderFilter;

	@Before
	public void setUp() throws Exception {
		mockChain = new MockFilterChain();
		req = new MockHttpServletRequest("/uploadfile", "/");
		rsp = new MockHttpServletResponse();
		correlationHeaderFilter = new CorrelationHeaderFilter();
		correlationHeaderFilter.init(new MockFilterConfig());
		correlationHeaderFilter.doFilter(req, rsp, new MockFilterChain());
	}

	@Test
	public void testFileStorageExceptionString() {
		try {
			throw new FileStorageException("Test");
		} catch (FileStorageException e) {
			assertEquals("Test", e.getMessage());
		}
	}

	@Test
	public void testFileStorageExceptionStringThrowable() {
		try {
			throw new FileStorageException("Test", new Throwable("Test"));
		} catch (FileStorageException e) {
			assertEquals("Test", e.getMessage());
		}
	}

}
