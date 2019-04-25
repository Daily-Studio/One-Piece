package org.dailystudio.onepiece.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dailystudio.onepiece.security.AccountContext;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNT")
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Column(name = "ACCOUNT_EMAIL", nullable = false)
    private String email;

    @Column(name = "ACCOUNT_PASSWORD", nullable = false)
    private String password;

    @Column(name = "ACCOUNT_ROLE", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccountRole accountRole;

    public AccountContext toAccountContext(){
        return AccountContext.fromAccountModel(this);
    }
}

//우리가 구현해야할 내용들
//1. 요청을 받아 처리할 필터
//2. Manager에 등록할 Auth Provider
//3. 인증 정보를 담을 DTO객체들
//4. 인증 성공/실패 핸들러
//5. 인증 성공/실패시 사용할 Authentication 객체