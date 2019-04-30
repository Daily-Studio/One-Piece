package org.dailystudio.onepiece.dto.account;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dailystudio.onepiece.domain.Account;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Slf4j
public class AccountSaveReq {

    private String email;
    private String password;

    public Account toEntity(PasswordEncoder passwordEncoder) {
        return Account.builder()
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .build();
    }
}
