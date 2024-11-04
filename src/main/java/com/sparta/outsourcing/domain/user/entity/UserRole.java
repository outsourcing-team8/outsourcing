package com.sparta.outsourcing.domain.user.entity;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("ROLE_USER"),
    OWNER("ROLE_OWNER");

    private String authority;

    UserRole(String authority) {
        this.authority = authority;
    }
}
