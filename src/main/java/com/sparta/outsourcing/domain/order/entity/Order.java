package com.sparta.outsourcing.domain.order.entity;

import com.sparta.outsourcing.common.entity.BaseEntity;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.order.dto.request.OrderAddReqDto;
import com.sparta.outsourcing.domain.order.enums.OrderStatus;
import com.sparta.outsourcing.domain.user.entity.Address;
import com.sparta.outsourcing.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import static com.sparta.outsourcing.domain.order.enums.OrderStatus.PENDING;

@Entity
@Table(name = "ORDERS")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "DELIVERY_ADDRESS")
    private String deliveryAddress;

    @Column(name = "CUSTOMER_PHONE_NUMBER")
    private String customerPhoneNumber;

    @Column(name = "REQUEST_MESSAGE")
    private String requestMessage;

    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    @Builder.Default
    private OrderStatus status = PENDING;

    @Column(name = "IS_DELETED")
    @Builder.Default
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    public static Order create(OrderAddReqDto request, User user, Menu menu) {
        Address userAddress = user.getAddress();
        String deliveryAddress = userAddress.getStreet() + " " + userAddress.getDetails() + " " + userAddress.getPostCode();

        return Order.builder()
                .amount(request.getAmount())
                .deliveryAddress(deliveryAddress)
                .customerPhoneNumber(user.getPhoneNumber())
                .requestMessage(request.getRequestMessage())
                .paymentMethod(request.getPaymentMethod())
                .user(user)
                .menu(menu)
                .build();
    }
}
