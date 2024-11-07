package com.sparta.outsourcing.domain.order.dto.response;

import com.sparta.outsourcing.domain.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderFindForUserRespDto {
    private Long orderId;
    private LocalDateTime orderedAt;
    private String status;
    private String storeName;
    private String menuName;
    private int amount;
    private int totalPrice;

    public static OrderFindForUserRespDto make(Order order) {
        int totalPrice = order.getAmount() * order.getMenu().getPrice();

        return OrderFindForUserRespDto.builder()
                .orderId(order.getOrderId())
                .orderedAt(order.getCreatedAt())
                .status(order.getStatus().name())
                .storeName(order.getMenu().getStore().getName())
                .menuName(order.getMenu().getName())
                .amount(order.getAmount())
                .totalPrice(totalPrice)
                .build();
    }
}
