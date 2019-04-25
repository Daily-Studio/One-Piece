package org.dailystudio.onepiece.security;

import org.dailystudio.onepiece.domain.Account;
import org.dailystudio.onepiece.domain.AccountRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 유저 정보를 인증과정에서 처리하기 위해서 만들어진 클래스이다.
 * UserDetails 라는 Spring Security에서 주어진 것을 사용하여 인증과정을 다룬다.
 */
public class AccountContext extends User {

    private AccountContext(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
    }

    public static AccountContext fromAccountModel(Account account) {
        return new AccountContext(account.getEmail(), account.getPassword(), parseAuthorites(account.getAccountRole()));
    }

    private static List<SimpleGrantedAuthority> parseAuthorites(AccountRole role) {
        return Arrays.asList(role).stream()
                .map(eachRole -> new SimpleGrantedAuthority(eachRole.getRoleName()))
                .collect(Collectors.toList());
    }

}
