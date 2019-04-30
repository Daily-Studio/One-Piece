package org.dailystudio.onepiece.security.jwt.filter;

import lombok.extern.slf4j.Slf4j;
import org.dailystudio.onepiece.security.jwt.JwtInfo;
import org.dailystudio.onepiece.security.jwt.handler.JwtFailureHandler;
import org.dailystudio.onepiece.security.token.JwtPreProcessingToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationFailureHandler failureHandler;

    //우리가 지정한 경로만 해당하도록
    protected JwtAuthenticationFilter(RequestMatcher matcher) {
        super(matcher);
    }

    public JwtAuthenticationFilter(RequestMatcher matcher, JwtFailureHandler failureHandler) {
        this(matcher);
        this.failureHandler = failureHandler;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        log.info("[request start] -> {}", req.getRequestURI());

        final String jwt = req.getHeader(JwtInfo.HEADER_NAME);
        if (StringUtils.isEmpty(jwt)) {
            throw new RuntimeException("토큰이 존재하지 않습니다.");
        }
        JwtPreProcessingToken preToken = new JwtPreProcessingToken(jwt);

        return super.getAuthenticationManager().authenticate(preToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(req, res);
        log.info("[request end] -> {}", req.getRequestURI());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
