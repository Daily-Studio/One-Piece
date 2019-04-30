package org.dailystudio.onepiece.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtPreProcessingToken extends UsernamePasswordAuthenticationToken {

    private JwtPreProcessingToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public JwtPreProcessingToken(final String token) {
        this(token, token.length());
    }
}
