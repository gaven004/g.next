package com.g.commons.web;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {
	/**
	 * Get the original IP address of the client
	 * 
	 * It depends from proxy server/load balancer and their configurations
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
			int i = ip.indexOf(',');
			if (i >= 0) {
				return ip.substring(0, i);
			} else {
				return ip;
			}
		}
		
		ip = request.getHeader("X-Real-IP");
		if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		
		ip = request.getHeader("Proxy-Client-IP");
		if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		
		ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		
		ip = request.getHeader("HTTP_CLIENT_IP");
		if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		return request.getRemoteAddr();
	}
}
