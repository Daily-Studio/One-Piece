package org.dailystudio.onepiece.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PreAuthorizationToken(String email, String password) {
        super(email, password);
    }

    public String getEmail() {
        return (String) super.getPrincipal();
    }

    public String getPassword() {
        return (String) super.getCredentials();
    }

    /**
     *
     * @param principal : 이 자리에 아이디
     * @param credentials : 이 자리에 패스워드가 들어간다고 생각하면 된다.
     */
//    public PreAuthorizationToken(Object principal, Object credentials) {
//        super(principal, credentials);
//    }
}
