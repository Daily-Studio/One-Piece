package org.dailystudio.onepiece.security.provider;

import lombok.RequiredArgsConstructor;
import org.dailystudio.onepiece.domain.Account;
import org.dailystudio.onepiece.repository.AccountRepository;
import org.dailystudio.onepiece.security.token.AccountContextService;
import org.dailystudio.onepiece.security.token.PostAuthorizationToken;
import org.dailystudio.onepiece.security.token.PreAuthorizationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private AccountContextService accountContextService;
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        PreAuthorizationToken token = (PreAuthorizationToken) authentication;
        String email = token.getEmail();
        String password = token.getPassword();

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("일치하는 아이디가 존재하지 않습니다."));

        if (isCorrectPassword(password, account)) {
            return PostAuthorizationToken.getTokenFromAccountContext(account.toAccountContext());
        }

        throw new BadCredentialsException("인증정보가 올바르지 않습니다.");
    }

    /**
     * 이 Provider가 어떠한 인증객체를 서포트할지 명시적으로 밝혀준다.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean isCorrectPassword(String password, Account account) {
        return passwordEncoder.matches(account.getPassword(), password);
    }
}