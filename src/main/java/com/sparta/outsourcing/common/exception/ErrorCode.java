package com.sparta.outsourcing.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(404, "해당 유저가 없습니다."),
    ALREADY_USER_EXIST(400, "존재하는 유저입니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
