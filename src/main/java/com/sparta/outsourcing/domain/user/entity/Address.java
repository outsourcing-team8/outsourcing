package com.sparta.outsourcing.domain.user.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Address {

    private String street; // 도로명 주소
    private String details; // 상세 주소
    private String postCode; // 우편 번호
}
