package com.sparta.outsourcing.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderAddReqDto {
	@NotNull
	private Long menuId;

	@NotNull
	private int amount;

	@NotNull
	private String requestMessage;

	@NotBlank
	private String paymentMethod;
}
