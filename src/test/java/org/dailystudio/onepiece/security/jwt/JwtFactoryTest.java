package org.dailystudio.onepiece.security.jwt;

import org.dailystudio.onepiece.domain.Account;
import org.dailystudio.onepiece.repository.AccountRepository;
import org.dailystudio.onepiece.security.AccountContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtFactoryTest {

    private final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    private AccountContext accountContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtFactory jwtFactory;

    @Before
    public void setUp() {
        Account account = accountRepository.findByEmail("example@gmail.com").orElseThrow(() -> new NoSuchElementException("데이터 존재하지 않음"));
        log.info("account idx : " + account.getId());
        log.info("account email : " + account.getEmail());
        log.info("account password : " + account.getPassword());
        log.info("account role : " + account.getAccountRole().getRoleName());
        this.accountContext = account.toAccountContext();
    }

    @Test
    public void JWT_토큰생성하기() {
        log.info("==============================================");
        log.info(jwtFactory.create(this.accountContext));
        assertThat(jwtFactory.create(this.accountContext)).containsPattern(".*\\..*\\..*");
        log.info("==============================================");
    }
}