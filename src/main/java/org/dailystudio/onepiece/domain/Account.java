package org.dailystudio.onepiece.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dailystudio.onepiece.security.AccountContext;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ACCOUNT")
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Column(name = "ACCOUNT_EMAIL")
    @NotNull
    private String email;

    @Column(name = "ACCOUNT_PASSWORD")
    @NotNull
    private String password;

    @Column(name = "ACCOUNT_ROLE")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private AccountRole accountRole;

    public AccountContext toAccountContext(){
        return new AccountContext(this);
    }
}

