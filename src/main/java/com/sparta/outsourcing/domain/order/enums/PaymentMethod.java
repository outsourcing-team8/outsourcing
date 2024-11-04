package com.sparta.outsourcing.domain.order.enums;

public enum PaymentMethod {
	CASH("CASH"),
	CREDIT_CARD("CREDIT CARD")
	;

	private final String method;

	PaymentMethod(String method) {
		this.method = method;
	}


}
