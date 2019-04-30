package org.dailystudio.onepiece.security.ajax.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.dailystudio.onepiece.dto.account.AccountLoginReqDto;
import org.dailystudio.onepiece.security.token.PreAuthorizationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AjaxLoginFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;

    public AjaxLoginFilter(String defaultFilterProcessesUrl, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler) {
        super(defaultFilterProcessesUrl);
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        AccountLoginReqDto loginReqDto = new ObjectMapper().readValue(req.getReader(), AccountLoginReqDto.class);
        PreAuthorizationToken preAuthorizationToken = loginReqDto.toPreAuthorizationToken();

        log.info("[request start] -> {}", req.getRequestURI());

        return super.getAuthenticationManager().authenticate(preAuthorizationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        this.authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }
}
