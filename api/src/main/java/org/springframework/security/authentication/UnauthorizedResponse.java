package org.springframework.security.authentication;

import com.g.commons.model.RestApiResponse;

public class UnauthorizedResponse<T> extends RestApiResponse<T> {
    public UnauthorizedResponse() {
        super("UNAUTHORIZED", "Lacks valid authentication credentials for the target resource");
    }

    public UnauthorizedResponse(String message) {
        super("UNAUTHORIZED", message);
    }

    public UnauthorizedResponse(String result, String message, T body) {
        super("UNAUTHORIZED", message, body);
    }
}
