package com.sparta.outsourcing.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
