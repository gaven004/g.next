package com.g.commons.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.g.commons.model.RestApiResponse;
import com.g.commons.web.RequestHelper;
import com.g.security.JwtService;
import com.g.security.PayloadKey;

@RestController
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    @Qualifier("restAuthenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("jwtService")
    private JwtService jwtService;

    @RequestMapping(path = "/test", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<RestApiResponse<String>> test() {
        RestApiResponse<String> result = RestApiResponse.success();
        result.setBody("OK!");
        return new ResponseEntity(result, null, HttpStatus.OK);
    }

    @RequestMapping(path = "/hello", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<RestApiResponse<String>> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        RestApiResponse<String> result = RestApiResponse.success();
        result.setBody("Hello " + name);
        return new ResponseEntity(result, null, HttpStatus.OK);
    }

    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<RestApiResponse<JwtAccessToken>> login(HttpServletRequest request, @Validated @RequestBody LoginUser user) {
        final boolean debug = logger.isDebugEnabled();

        RestApiResponse<JwtAccessToken> response = null;
        try {
            Authentication authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            if (debug) {
                logger.debug("Try authenticate for user: {}", user.getUsername());
            }

            Authentication authResult = authenticationManager.authenticate(authRequest);
            if (debug) {
                logger.debug("Authentication success: {}", authResult);
            }

            Map<String, Object> payloads = new HashMap<>();
            payloads.put(PayloadKey.username, user.getUsername());
            payloads.put(PayloadKey.ip, RequestHelper.getClientIpAddr(request));

            final Map<String, Object> signResult = jwtService.sign(payloads);

            JwtAccessToken accessToken = new JwtAccessToken();
            accessToken.setUsername(user.getUsername());
            accessToken.setIssuer((String) signResult.get(JwtService.ISSUER));
            accessToken.setIssuerAt((Date) signResult.get(JwtService.ISSUED_AT));
            accessToken.setExpiresAt((Date) signResult.get(JwtService.EXPIRES_AT));
            accessToken.setToken((String) signResult.get(JwtService.TOKEN));

            response = RestApiResponse.success();
            response.setBody(accessToken);
        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();

            if (debug) {
                logger.debug("Authentication request for failed: {}", failed.getMessage());
            }

            response = RestApiResponse.error(failed.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.set("Content-Type", "application/json;charset=UTF-8");

        return new ResponseEntity(response, headers, HttpStatus.OK);
    }
}
