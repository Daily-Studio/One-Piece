package org.dailystudio.onepiece.domain;

import lombok.Getter;

@Getter
public enum AccountRole {
    USER("유저"),
    ADMIN("관리자");

    private String roleName;

    AccountRole(String roleName) {
        this.roleName = roleName;
    }


}
