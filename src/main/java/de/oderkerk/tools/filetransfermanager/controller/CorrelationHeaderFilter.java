package de.oderkerk.tools.filetransfermanager.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class CorrelationHeaderFilter implements Filter {

	private final static String CORRELATION_ID_HEADER = "X-Correlation-ID";
	private static final ThreadLocal<String> transactionId = new ThreadLocal<String>();
	private final Logger logger = LogManager.getLogger(CorrelationHeaderFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String currentCorrId = httpServletRequest.getHeader(CORRELATION_ID_HEADER);

		if (currentCorrId == null) {
			currentCorrId = UUID.randomUUID().toString();
			logger.info("No correlationId found in Header. Generated : " + currentCorrId);
		} else {
			logger.info("Found correlationId in Header : " + currentCorrId);
		}
		transactionId.set(currentCorrId);
		filterChain.doFilter(httpServletRequest, servletResponse);
	}

	/**
	 * @return the transactionid
	 */
	public static ThreadLocal<String> getTransactionid() {
		return transactionId;
	}

}
