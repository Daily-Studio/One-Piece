package org.dailystudio.onepiece.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dailystudio.onepiece.security.context.AccountContext;

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

    @Column(name = "ACCOUNT_EMAIL", unique = true)
    @NotNull
    private String email;

    @Column(name = "ACCOUNT_PASSWORD")
    @NotNull
    private String password;

    @Column(name = "ACCOUNT_ROLE")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private AccountRole accountRole;

    @Builder
    public Account(@NotNull String email, @NotNull String password) {
        this.email = email;
        this.password = password;
        this.accountRole = AccountRole.USER;
    }

    public AccountContext toAccountContext() {
        return new AccountContext(this);
    }
}

