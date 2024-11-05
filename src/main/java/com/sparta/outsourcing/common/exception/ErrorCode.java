package com.sparta.outsourcing.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    STORE_NOT_FOUND(404, "해당 가게가 없습니다."),

    USER_NOT_FOUND(404, "해당 유저가 없습니다."),
    ALREADY_USER_EXIST(400, "존재하는 유저입니다."),
    MISS_MATCH_PASSWORD(400, "패스워드 검증에 실패하였습니다."),

    TOO_MANY_STORES(409, "이미 3개의 가게를 가지고 있습니다."),
    ALREADY_STORE_EXIST(409, "같은 상호명의 가게가 이미 존재합니다."),
    AUTHENTICATION_ERROR(500, "서버에러입니다."),
    INVALID_TOKEN_ERROR(401, "잘못된 토큰 정보입니다."),
    TOKEN_NOT_FOUND(401, "토큰 정보를 찾을 수 없습니다."),
    AUTHORITY_NOT_FOUND(403, "권한 정보를 찾을 수 없습니다."),

    MENU_NOT_FOUND(404, "해당 메뉴가 없습니다."),

    PAYMENT_METHOD_NOT_SUPPORT(400, "지원하지 않은 결제 방식 입니다."),
    MIN_PRICE_NOT_MET(400, "최소주문금액을 만족하지 않습니다."),
    ;

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
