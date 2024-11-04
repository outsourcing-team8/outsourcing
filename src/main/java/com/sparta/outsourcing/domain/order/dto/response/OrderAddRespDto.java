package com.sparta.outsourcing.domain.order.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderAddRespDto {
    private Long orderId;
}
