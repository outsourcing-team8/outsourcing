package com.sparta.outsourcing.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    STORE_NOT_FOUND(404,"해당 가게가 없습니다."),

    USER_NOT_FOUND(404, "해당 유저가 없습니다."),
    ALREADY_USER_EXIST(400, "존재하는 유저입니다."),

    TOO_MANY_STORES(409, "이미 3개의 가게를 가지고 있습니다."),
    ALREADY_STORE_EXIST(409, "같은 상호명의 가게가 이미 존재합니다."),

    ORDER_NOT_FOUND(404, "해당 주문이 없습니다."),
    NOT_ORDERED(401, "주문자만 리뷰를 작성할 수 있습니다."),
    ALREADY_REVIEWED_ORDER(409, "이미 리뷰한 주문입니다."),
    ;

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
