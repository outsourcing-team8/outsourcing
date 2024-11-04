package com.sparta.outsourcing.domain.order.controller;

import com.sparta.outsourcing.domain.order.dto.request.OrderAddReqDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderAddRespDto;
import com.sparta.outsourcing.domain.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	// 인증/인가 구현이 완료되면 인증 정보(= 요청 사용자 정보)를 통해 주문 생성 예정
	// 임시로 path parameter 사용
	@PostMapping("/{userId}")
	public OrderAddRespDto addOrder(@RequestBody @Valid OrderAddReqDto request,
									@PathVariable(name = "userId") Long userId) {
		return orderService.add(request, userId);
	}
}
