package com.sparta.outsourcing.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderUpdateStatusReqDto {
    @NotNull
    private Long orderId;

    @NotBlank
    private String status;
}
