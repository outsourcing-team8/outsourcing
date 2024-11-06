package com.sparta.outsourcing.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    //user error
    USER_NOT_FOUND(404, "해당 유저가 없습니다."),
    ALREADY_USER_EXIST(400, "존재하는 유저입니다."),
    MISS_MATCH_PASSWORD(400, "패스워드 검증에 실패하였습니다."),


    //menu error
    MENU_NOT_FOUND(404, "해당 메뉴가 없습니다."),
    STORE_NOT_OWN(400,"헤당 메뉴가 등록된 가게가 아닙니다."),
    NO_AUTHORITY(403,"권한이 없습니다."),
    DUPLICATE_MENU_NAME(400,"중복 된 메뉴입니다" ),

    //store error
    TOO_MANY_STORES(409, "이미 3개의 가게를 가지고 있습니다."),
    ALREADY_STORE_EXIST(409, "같은 상호명의 가게가 이미 존재합니다."),
    STORE_NOT_FOUND(404, "해당 가게가 없습니다."),
    NOT_STORE_OWNER(401, "해당 가게의 사장이 아닙니다."),

    // auth error
    AUTHENTICATION_ERROR(500, "서버에러입니다."),
    INVALID_TOKEN_ERROR(401, "잘못된 토큰 정보입니다."),
    TOKEN_NOT_FOUND(401, "토큰 정보를 찾을 수 없습니다."),
    AUTHORITY_NOT_FOUND(403, "권한 정보를 찾을 수 없습니다."),

    // order error
    ORDER_NOT_FOUND(404, "해당 주문이 없습니다."),
    MIN_PRICE_NOT_MET(400, "최소주문금액을 만족하지 않습니다."),
    NOT_BUSINESS_HOURS(400, "가게 영업시간이 아닙니다."),
    SELECTED_DATE_NOT_VALID(400, "날짜 형식이 올바르지 않습니다."),
    NOT_ORDER_USER(401, "주문 고객이 아닙니다."),
    CAN_NOT_CANCEL_ORDER(400, "주문은 가게에서 주문 수락전일 때만 가능합니다."),

    // review error
    PAYMENT_METHOD_NOT_SUPPORT(400, "지원하지 않은 결제 방식 입니다."),
    REVIEW_NOT_FOUND(404, "해당 리뷰가 없습니다."),
    NOT_ORDERED(401, "주문자만 리뷰를 작성할 수 있습니다."),
    ALREADY_REVIEWED_ORDER(409, "이미 리뷰한 주문입니다."),
    ORDER_STATUS_NOT_FOUND(400, "요청 주문상태와 일치하는 주문상태 상수를 찾을 수 없습니다."),
    ALREADY_CANCEL_ORDER(400, "이미 취소된 주문입니다."),
    ;

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
