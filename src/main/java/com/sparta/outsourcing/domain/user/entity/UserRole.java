package com.sparta.outsourcing.domain.user.entity;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum UserRole {
    USER("ROLE_USER"),
    OWNER("ROLE_OWNER");

    private String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public static UserRole fromAuthority(String authority) {
        for (UserRole value : values()) {
            if (value.getAuthority().equals(authority)) {
                return value;
            }
        }
        throw new CustomApiException(ErrorCode.AUTHORITY_NOT_FOUND);
    }
}
