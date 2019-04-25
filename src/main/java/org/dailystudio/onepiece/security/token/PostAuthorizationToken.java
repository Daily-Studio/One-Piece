package org.dailystudio.onepiece.security.token;

import org.dailystudio.onepiece.security.AccountContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private PostAuthorizationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static PostAuthorizationToken getTokenFromAccountContext(AccountContext accountContext){
        return new PostAuthorizationToken(accountContext.getUsername(),accountContext.getPassword(),accountContext.getAuthorities());
    }

}
