package com.sparta.outsourcing.domain.order.controller;

import com.sparta.outsourcing.common.security.LoginUser;
import com.sparta.outsourcing.domain.order.dto.request.OrderAddReqDto;
import com.sparta.outsourcing.domain.order.dto.request.OrderCancelReqDto;
import com.sparta.outsourcing.domain.order.dto.request.OrderUpdateStatusReqDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderAddRespDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderFindDetailRespDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderListForOwnerRespDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderListForUserRespDto;
import com.sparta.outsourcing.domain.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderAddRespDto addOrder(@RequestBody @Valid OrderAddReqDto request,
									@AuthenticationPrincipal LoginUser loginUser) {
		Long loginUserId = loginUser.getUser().getUserId();
		return orderService.add(request, loginUserId);
	}

	@GetMapping("/{orderId}")
	@ResponseStatus(HttpStatus.OK)
	public OrderFindDetailRespDto findOrderDetail(@PathVariable(name = "orderId") Long orderId,
											@AuthenticationPrincipal LoginUser loginUser) {
		Long loginUserId = loginUser.getUser().getUserId();
		return orderService.findOrder(loginUserId, orderId);
	}

	@GetMapping("/user/search-condition")
	@ResponseStatus(HttpStatus.OK)
	public OrderListForUserRespDto findOrderHistory(@AuthenticationPrincipal LoginUser loginUser,
													@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
													@RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
		Long loginUserId = loginUser.getUser().getUserId();
		PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(DESC, "created_at"));
		return orderService.findAllByUserId(loginUserId, pageRequest);
	}

	@GetMapping("/owner/search-condition")
	@ResponseStatus(HttpStatus.OK)
	public OrderListForOwnerRespDto findOrderList(@AuthenticationPrincipal LoginUser loginUser,
												  @RequestParam(name = "storeId", defaultValue = "0") Long storeId,
												  @RequestParam(name = "selectedDate", defaultValue = "") String selectedDate,
												  @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
												  @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
		Long loginUserId = loginUser.getUser().getUserId();
		PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(ASC, "createdAt"));
		return orderService.findAllByOwnerId(loginUserId, storeId, selectedDate, pageRequest);
	}

	@PatchMapping("/cancel")
	@ResponseStatus(HttpStatus.OK)
	public void cancelOrder(@AuthenticationPrincipal LoginUser loginUser,
							@RequestBody @Valid OrderCancelReqDto request) {
		Long loginUserId = loginUser.getUser().getUserId();
		orderService.cancelOrder(loginUserId, request);
	}

	@PatchMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void updateOrderStatus(@AuthenticationPrincipal LoginUser loginUser,
								  @RequestBody @Valid OrderUpdateStatusReqDto request) {
		Long loginUserId = loginUser.getUser().getUserId();
		orderService.updateOrderStatus(loginUserId, request);
	}

	@DeleteMapping("/{orderId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteOrder(@AuthenticationPrincipal LoginUser loginUser,
							@PathVariable(name = "orderId") Long orderId) {
		Long loginUserId = loginUser.getUser().getUserId();
		orderService.deleteOrder(loginUserId, orderId);
	}
}
