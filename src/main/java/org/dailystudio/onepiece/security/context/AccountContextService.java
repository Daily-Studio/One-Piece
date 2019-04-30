package org.dailystudio.onepiece.security.context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dailystudio.onepiece.domain.Account;
import org.dailystudio.onepiece.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

/**
 * UserDetailsService와 같은 역할을 한다고 생각하면 된다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AccountContextService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("일치하는 아이디가 존재하지 않습니다."));
        return account.toAccountContext();
    }

}
