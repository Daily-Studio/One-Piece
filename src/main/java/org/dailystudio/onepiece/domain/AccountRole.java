package org.dailystudio.onepiece.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
public enum AccountRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String roleName;

    AccountRole(String roleName) {
        this.roleName = roleName;
    }


    public static AccountRole getRoleByName(String role) {
        return Arrays.stream(AccountRole.values())
                .filter(eachRole -> eachRole.isCorrectName(role))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("권한이 없습니다."));
    }

    private boolean isCorrectName(String role) {
        return role.equalsIgnoreCase(this.roleName);
    }
}
