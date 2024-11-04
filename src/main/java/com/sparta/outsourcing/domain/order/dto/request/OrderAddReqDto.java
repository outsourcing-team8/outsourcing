package com.sparta.outsourcing.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class OrderAddReqDto {
	@NotNull
	private Long menuId;

	@NotNull
	private int amount;

	@NotNull
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
	private String customerPhoneNumber;

	@NotEmpty
	private String deliveryAddress;

	@NotNull
	private String requestMessage;

	@NotBlank
	private String paymentMethod;
}
