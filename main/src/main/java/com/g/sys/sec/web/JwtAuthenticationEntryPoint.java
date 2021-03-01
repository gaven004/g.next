package com.g.sys.sec.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@Service
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private String typeName = "Bearer";

    private final String realmName;

    private RestExceptionRenderer exceptionRenderer = new RestExceptionRenderer();
    // This is from Spring MVC.
    private HandlerExceptionResolver handlerExceptionResolver = new DefaultHandlerExceptionResolver();

    public JwtAuthenticationEntryPoint(@Value("${sec.entryPoint.realmName:G.Next Open Api}") String realmName) {
        Assert.hasText(realmName, "[Assertion failed] - the realmName argument must have text; it must not be null, empty, or blank");

        this.realmName = realmName;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        try {
            ResponseEntity<?> result = new ResponseEntity<UnauthorizedResponse>(new UnauthorizedResponse(authException.getMessage()), HttpStatus.UNAUTHORIZED);
            result = enhanceResponse(result, authException);
            exceptionRenderer.handleHttpEntityResponse(result, new ServletWebRequest(request, response));
            response.flushBuffer();
        } catch (ServletException e) {
            // Re-use some of the default Spring dispatcher behaviour - the exception came from the filter chain and
            // not from an MVC handler so it won't be caught by the dispatcher (even if there is one)
            if (handlerExceptionResolver.resolveException(request, response, this, e) == null) {
                throw e;
            }
        } catch (IOException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            // Wrap other Exceptions. These are not expected to happen
            throw new RuntimeException(e);
        }
    }

    protected ResponseEntity<?> enhanceResponse(ResponseEntity<?> response, Exception exception) {
        HttpHeaders headers = response.getHeaders();
        String existing = null;
        if (headers.containsKey("WWW-Authenticate")) {
            existing = extractTypePrefix(headers.getFirst("WWW-Authenticate"));
        }
        StringBuilder builder = new StringBuilder();
        builder.append(typeName + " ");
        builder.append("realm=\"" + realmName + "\"");
        if (existing != null) {
            builder.append(", " + existing);
        }
        HttpHeaders update = new HttpHeaders();
        update.putAll(response.getHeaders());
        update.set("WWW-Authenticate", builder.toString());
        return new ResponseEntity<Object>(response.getBody(), update, response.getStatusCode());
    }

    private String extractTypePrefix(String header) {
        String existing = header;
        String[] tokens = existing.split(" +");
        if (tokens.length > 1 && !tokens[0].endsWith(",")) {
            existing = StringUtils.arrayToDelimitedString(tokens, " ").substring(existing.indexOf(" ") + 1);
        }
        return existing;
    }
}
