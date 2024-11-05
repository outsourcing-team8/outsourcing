package com.sparta.outsourcing.domain.order.controller;

import com.sparta.outsourcing.common.security.LoginUser;
import com.sparta.outsourcing.domain.order.dto.request.OrderAddReqDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderAddRespDto;
import com.sparta.outsourcing.domain.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public OrderAddRespDto addOrder(@RequestBody @Valid OrderAddReqDto request,
									@AuthenticationPrincipal LoginUser loginUser) {
		Long loginUserId = loginUser.getUser().getUserId();
		return orderService.add(request, loginUserId);
	}
}
