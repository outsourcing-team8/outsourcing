package com.sparta.outsourcing.domain.order.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderCancelReqDto {
    @NotNull
    private Long orderId;
}
