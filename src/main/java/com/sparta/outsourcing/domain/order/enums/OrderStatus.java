package com.sparta.outsourcing.domain.order.enums;

import com.sparta.outsourcing.common.exception.CustomApiException;

import static com.sparta.outsourcing.common.exception.ErrorCode.ORDER_STATUS_NOT_FOUND;

public enum OrderStatus {
	// customer
	CANCEL,

	// owner
	PENDING,
	COOKING,
	ON_DELIVERY,
	DELIVERY_COMPLETED,
	REFUSAL
	;

	public static OrderStatus findByName(String statusName) {
		for (OrderStatus orderStatus : OrderStatus.values()) {
			if (orderStatus.name().equals(statusName)) return orderStatus;
		}
		throw new CustomApiException(ORDER_STATUS_NOT_FOUND);
	}
}
