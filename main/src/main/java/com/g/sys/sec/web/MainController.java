package com.g.sys.sec.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.g.commons.exception.ErrorCode;
import com.g.commons.model.AntdResponse;
import com.g.commons.web.RequestHelper;
import com.g.sys.sec.model.LoginUser;
import com.g.sys.sec.model.PayloadKey;
import com.g.sys.sec.model.SecurityUser;
import com.g.sys.sec.model.UserInfo;
import com.g.sys.sec.service.JwtService;

@RestController
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Autowired
    public MainController(AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        Assert.notNull(authenticationManager, "A AuthenticationManager must be set");
        Assert.notNull(jwtService, "A JwtService must be set");

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @RequestMapping(path = "/test")
    public ResponseEntity<AntdResponse<String>> test() {
        AntdResponse<String> result = AntdResponse.success("OK!");
        return new ResponseEntity(result, null, HttpStatus.OK);
    }

    @RequestMapping(path = "/hello", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AntdResponse<String>> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        AntdResponse<String> result = AntdResponse.success("Hello " + name);
        return new ResponseEntity(result, null, HttpStatus.OK);
    }

    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AntdResponse<UserInfo>> login(HttpServletRequest request, @Validated @RequestBody LoginUser user) {
        final boolean debug = logger.isDebugEnabled();

        AntdResponse<UserInfo> response = null;
        try {
            Authentication authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            if (debug) {
                logger.debug("Try authenticate for user: {}", user.getUsername());
            }

            Authentication authResult = authenticationManager.authenticate(authRequest);
            if (debug) {
                logger.debug("Authentication success: {}", authResult);
            }

            SecurityUser authUser = (SecurityUser) authResult.getPrincipal();
            response = AntdResponse.success(getUserInfo(request, authUser));
        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();

            if (debug) {
                logger.debug("Authentication request for failed: {}", failed.getMessage());
            }

            response = AntdResponse.error(ErrorCode.Generic, failed.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.set("Content-Type", "application/json;charset=UTF-8");

        return new ResponseEntity(response, headers, HttpStatus.OK);
    }

    @RequestMapping(path = "/refresh-token")
    public AntdResponse<UserInfo> refreshToken(HttpServletRequest request) {
        SecurityUser authUser = WebSecurityHelper.getAuthUser();
        return AntdResponse.success(getUserInfo(request, authUser));
    }

    private UserInfo getUserInfo(HttpServletRequest request, SecurityUser authUser) {
        Map<String, Object> payloads = new HashMap<>();
        payloads.put(PayloadKey.username, authUser.getUsername());
        payloads.put(PayloadKey.ip, RequestHelper.getClientIpAddr(request));

        final Map<String, Object> signResult = jwtService.sign(payloads);

        UserInfo accessToken = new UserInfo();
        accessToken.setAccount(authUser.getUsername());
        accessToken.setUsername(authUser.getNickname());
        accessToken.setIssuer((String) signResult.get(JwtService.ISSUER));
        accessToken.setIssuerAt((Date) signResult.get(JwtService.ISSUED_AT));
        accessToken.setExpiresAt((Date) signResult.get(JwtService.EXPIRES_AT));
        accessToken.setToken((String) signResult.get(JwtService.TOKEN));
        return accessToken;
    }
}
