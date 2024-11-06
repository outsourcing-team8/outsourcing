package com.sparta.outsourcing.domain.order.dto.response;

import com.sparta.outsourcing.domain.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderFindDetailRespDto {
    private Long orderId;
    private String status;
    private String storeName;
    private LocalDateTime orderedAt;
    private String menuName;
    private int menuPrice;
    private int amount;
    private int totalPrice;
    private int paymentPrice;
    private String paymentMethod;
    private String deliveryAddress;
    private String phoneNumber;
    private String message;

    public static OrderFindDetailRespDto make(Order order, String storeName) {
        int totalPrice = order.getAmount() * order.getMenu().getPrice();
        return OrderFindDetailRespDto.builder()
                .orderId(order.getOrderId())
                .status(order.getStatus().name())
                .storeName(storeName)
                .orderedAt(order.getCreatedAt())
                .menuName(order.getMenu().getName())
                .menuPrice(order.getMenu().getPrice())
                .amount(order.getAmount())
                .totalPrice(totalPrice)
                .paymentPrice(totalPrice)
                .paymentMethod(order.getPaymentMethod())
                .deliveryAddress(order.getDeliveryAddress())
                .phoneNumber(order.getCustomerPhoneNumber())
                .message(order.getRequestMessage())
                .build();
    }
}
