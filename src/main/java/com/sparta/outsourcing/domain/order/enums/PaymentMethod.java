package com.sparta.outsourcing.domain.order.enums;

import com.sparta.outsourcing.common.exception.CustomApiException;

import static com.sparta.outsourcing.common.exception.ErrorCode.PAYMENT_METHOD_NOT_SUPPORT;

public enum PaymentMethod {
	CASH("CASH"),
	CREDIT_CARD("CREDIT CARD")
	;

	private final String value;

	PaymentMethod(String value) {
		this.value = value;
	}

	public static void checkPaymentMethod(String value) {
		boolean isValid = false;
		for (PaymentMethod paymentMethod : PaymentMethod.values()) {
			if (paymentMethod.value.equals(value)) {
				isValid = true;
				break;
			}
		}

		if (!isValid) throw new CustomApiException(PAYMENT_METHOD_NOT_SUPPORT);
	}
}
