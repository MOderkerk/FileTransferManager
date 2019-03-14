package de.oderkerk.tools.filetransfermanager.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class CorrelationHeaderFilterTest {

	MockFilterChain mockChain;
	MockHttpServletRequest req;
	MockHttpServletResponse rsp;
	CorrelationHeaderFilter correlationHeaderFilter;

	@Before
	public void setUp() throws Exception {
		mockChain = new MockFilterChain();
		req = new MockHttpServletRequest("/uploadfile", "/");
		rsp = new MockHttpServletResponse();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInit() {
		correlationHeaderFilter = new CorrelationHeaderFilter();
		try {
			correlationHeaderFilter.init(new MockFilterConfig());
		} catch (ServletException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDoFilter() {
		correlationHeaderFilter = new CorrelationHeaderFilter();
		try {
			correlationHeaderFilter.doFilter(req, rsp, new MockFilterChain());
			assertNotNull(CorrelationHeaderFilter.getTransactionid());
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ServletException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDoFilterGivenID() {
		correlationHeaderFilter = new CorrelationHeaderFilter();
		try {
			req.addHeader("X-Correlation-ID", "4711");
			correlationHeaderFilter.doFilter(req, rsp, new MockFilterChain());
			assertNotNull(CorrelationHeaderFilter.getTransactionid());
			assertEquals("4711", CorrelationHeaderFilter.getTransactionid().get());
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ServletException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDoFilterException() {
		correlationHeaderFilter = new CorrelationHeaderFilter();
		try {
			req.addHeader("X-Correlation-ID", "4711");
			correlationHeaderFilter.doFilter(null, rsp, new MockFilterChain());
			fail("No exception thrown but one was expected");
		} catch (Exception e) {
			assertTrue(true);
		}
	}

}
