package org.dailystudio.onepiece.security.context;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.dailystudio.onepiece.domain.Account;
import org.dailystudio.onepiece.domain.AccountRole;
import org.dailystudio.onepiece.security.jwt.JwtInfo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 유저 정보를 인증과정에서 처리하기 위해서 만들어진 클래스이다.
 * UserDetails 라는 Spring Security에서 주어진 것을 사용하여 인증과정을 다룬다.
 */
@Slf4j
public class AccountContext extends User {

//    //기본생성자
//    private AccountContext(String idx, String password, Collection<? extends GrantedAuthority> authorities) {
//        super(idx, password, authorities);
//    }

    public AccountContext(Account account) {
        super(account.getId().toString(), account.getPassword(), parseAuthorites(account.getAccountRole()));
    }

    public AccountContext(DecodedJWT decodedJWT) {
        super(decodedJWT.getClaim(JwtInfo.USER_IDX).asString(),
                "",
                parseAuthorites(decodedJWT.getClaim(JwtInfo.USER_AUTHORITY).asString()));
    }

    private static List<SimpleGrantedAuthority> parseAuthorites(AccountRole role) {
        return Arrays.asList(role).stream()
                .map(eachRole -> new SimpleGrantedAuthority(eachRole.getRoleName()))
                .collect(Collectors.toList());
    }

    private static List<SimpleGrantedAuthority> parseAuthorites(String role) {
        return parseAuthorites(AccountRole.getRoleByName(role));
    }

}
