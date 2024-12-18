package com.sparta.outsourcing.domain.store.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class StorePatchReqDto {

    @NotNull(message = "영업 시작 시간을 입력해주세요.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openAt;

    @NotNull(message = "영업 마감 시간을 입력해주세요.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closedAt;

    @NotNull(message = "최소 주문 가격을 입력해주세요.")
    @Range(min = 100, max = 100_000_000, message = "최소 100원부터 최대 1억원 까지만 입력해주세요.")
    private int minPrice;
}
