package com.g.commons.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.g.commons.utils.DateUtil;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessLogFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(AccessLogFilter.class);

	@SuppressWarnings("unused")
	private FilterConfig filterConfig = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
			throw new ServletException("AccessLogFilter just supports HTTP requests");
		}

		long start = System.currentTimeMillis();

		chain.doFilter(request, response);

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String remotehost = RequestHelper.getClientIpAddr(httpServletRequest);
		String user = httpServletRequest.getUserPrincipal() == null ? "-"
				: httpServletRequest.getUserPrincipal().getName();
		String requestUrl = UrlUtils.buildRequestUrl(httpServletRequest);
		String date = DateUtil.format(new Date(), DateUtil.DF_YYYY_MM_DD_HH_MM_SS);
		long end = System.currentTimeMillis();

		logger.info("{} {} [{}] \"{} {}\" {}", remotehost, user, date, httpServletRequest.getMethod(), requestUrl,
				end - start);
	}

	@Override
	public void destroy() {
		this.filterConfig = null;
	}
}
