package com.sparta.outsourcing.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(404, "해당 유저가 없습니다."),
    ALREADY_USER_EXIST(400, "존재하는 유저입니다."),
    MISS_MATCH_PASSWORD(400, "패스워드 검증에 실패하였습니다."),

    TOO_MANY_STORES(409, "이미 3개의 가게를 가지고 있습니다."),
    ALREADY_STORE_EXIST(409, "같은 상호명의 가게가 이미 존재합니다."),
    STORE_NOT_FOUND(404, "해당 가게가 없습니다."),
    NOT_STORE_OWNER(401, "해당 가게의 사장이 아닙니다."),
    ;

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
