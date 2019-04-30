package org.dailystudio.onepiece.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dailystudio.onepiece.domain.Account;
import org.dailystudio.onepiece.dto.account.AccountSaveReq;
import org.dailystudio.onepiece.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean save(final AccountSaveReq saveReq) {
        if (accountRepository.findByEmail(saveReq.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 계정입니다.");
        }
        Account account = saveReq.toEntity(passwordEncoder);
        accountRepository.save(account);
        return true;
    }

    public Account getAccount(final Long userIdx) {
        return accountRepository.findById(userIdx).orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다."));
    }
}
