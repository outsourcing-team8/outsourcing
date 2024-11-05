package com.sparta.outsourcing.domain.order.dto.response;

import com.sparta.outsourcing.domain.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderAddRespDto {
    private Long orderId;

    public static OrderAddRespDto make(Order order) {
        return OrderAddRespDto.builder()
                .orderId(order.getOrderId())
                .build();
    }
}
