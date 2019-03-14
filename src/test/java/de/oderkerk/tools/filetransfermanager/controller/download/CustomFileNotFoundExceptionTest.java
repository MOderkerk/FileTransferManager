package de.oderkerk.tools.filetransfermanager.controller.download;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import de.oderkerk.tools.filetransfermanager.controller.CorrelationHeaderFilter;

public class CustomFileNotFoundExceptionTest {
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
	public void testCustomFileNotFoundExceptionStringThrowable() {
		try {
			throw new CustomFileNotFoundException("Test", new Throwable("Test"));
		} catch (CustomFileNotFoundException e) {
			assertEquals("Test", e.getMessage());
		}
	}

	@Test
	public void testCustomFileNotFoundExceptionString() {
		try {
			throw new CustomFileNotFoundException("Test");
		} catch (CustomFileNotFoundException e) {
			assertEquals("Test", e.getMessage());
		}
	}

}
