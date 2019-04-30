package org.dailystudio.onepiece.security.jwt.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException exception) throws IOException, ServletException {
        log.warn("[BadCredentialsException] credentials exception {}", exception.getMessage());
        processResponse(res);
        log.info("[request failure] -> {}", req.getRequestURI());
    }

    public void processResponse(HttpServletResponse res) throws IOException {
        res.setContentType(MediaType.APPLICATION_JSON.toString());
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setCharacterEncoding("UTF-8");
    }
}
