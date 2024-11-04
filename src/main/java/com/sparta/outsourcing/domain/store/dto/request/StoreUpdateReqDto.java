package com.sparta.outsourcing.domain.store.dto.request;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
public class StoreUpdateReqDto {

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openAt;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closedAt;

    @Range(min = 100, max = 100_000_000, message = "최소 100원부터 최대 1억원 까지만 입력해주세요.")
    private Integer minPrice;
}
