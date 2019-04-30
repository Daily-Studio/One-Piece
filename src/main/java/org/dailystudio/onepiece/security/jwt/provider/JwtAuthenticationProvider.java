package org.dailystudio.onepiece.security.jwt.provider;

import org.dailystudio.onepiece.security.context.AccountContext;
import org.dailystudio.onepiece.security.token.PostAuthorizationToken;
import org.dailystudio.onepiece.security.jwt.JwtFactory;
import org.dailystudio.onepiece.security.token.JwtPreProcessingToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtPreProcessingToken preToken = (JwtPreProcessingToken) authentication;
        String jwt = preToken.getName();
        if (!JwtFactory.isValid(jwt)) {
            throw new BadCredentialsException("유효하지 않은 토큰입니다.");
        }
        AccountContext context = JwtFactory.getAccountContext(jwt);
        return PostAuthorizationToken.getTokenFromJwtAccountContext(context);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreProcessingToken.class.isAssignableFrom(authentication);
    }
}
