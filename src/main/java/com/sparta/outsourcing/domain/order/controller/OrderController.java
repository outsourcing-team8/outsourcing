package com.sparta.outsourcing.domain.order.controller;

import com.sparta.outsourcing.common.security.LoginUser;
import com.sparta.outsourcing.domain.order.dto.request.OrderAddReqDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderAddRespDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderListForUserRespDto;
import com.sparta.outsourcing.domain.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

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

	@GetMapping("/user/search-condition")
	public OrderListForUserRespDto findOrderHistory(@AuthenticationPrincipal LoginUser loginUser,
													@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
													@RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
		Long loginUserId = loginUser.getUser().getUserId();
		PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(DESC, "created_at"));
		return orderService.findAllByUserId(loginUserId, pageRequest);
	}

	@DeleteMapping("/{orderId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteOrder(@AuthenticationPrincipal LoginUser loginUser,
							@PathVariable(name = "orderId") Long orderId) {
		Long loginUserId = loginUser.getUser().getUserId();
		// orderService.deleteOrder() 호출 예정
	}
}
