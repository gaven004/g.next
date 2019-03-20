package org.springframework.security.web.authentication.www;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.JwtAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.g.commons.web.RequestHelper;

public class BearerTokenExtractor {
    public static String BEARER_TYPE = "Bearer";

    public static String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");
        while (headers.hasMoreElements()) { // typically there is only one (most servers enforce that)
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                return authHeaderValue;
            }
        }

        return null;
    }

    public static Authentication extract(HttpServletRequest request) {
        String tokenValue = extractHeaderToken(request);
        String ip = RequestHelper.getClientIpAddr(request);
        if (tokenValue != null) {
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(tokenValue, ip);
            return authentication;
        }
        return null;
    }
}
