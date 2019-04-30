package org.dailystudio.onepiece.dto.account;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dailystudio.onepiece.security.token.PreAuthorizationToken;

@Getter
@Slf4j
public class AccountLoginReqDto {

    private String email;
    private String password;

    public PreAuthorizationToken toPreAuthorizationToken() {
        return new PreAuthorizationToken(this.email, this.password);
    }
}
