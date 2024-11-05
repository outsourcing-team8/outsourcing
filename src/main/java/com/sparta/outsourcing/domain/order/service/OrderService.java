package com.sparta.outsourcing.domain.order.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.order.dto.request.OrderAddReqDto;
import com.sparta.outsourcing.domain.order.dto.request.OrderUpdateStatusReqDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderAddRespDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderFindForUserRespDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderListForUserRespDto;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.enums.OrderStatus;
import com.sparta.outsourcing.domain.order.enums.PaymentMethod;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.order.validator.OrderValidator;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sparta.outsourcing.common.exception.ErrorCode.*;
import static com.sparta.outsourcing.domain.order.enums.OrderStatus.CANCEL;
import static com.sparta.outsourcing.domain.order.enums.OrderStatus.findByName;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final MenuRepository menuRepository;
	private final UserRepository userRepository;
	private final OrderValidator orderValidator;

	@Transactional
	public OrderAddRespDto add(OrderAddReqDto request, Long loginUserId) {
		PaymentMethod.checkPaymentMethod(request.getPaymentMethod());

		Menu foundMenu = menuRepository.findById(request.getMenuId())
				.orElseThrow(() -> new CustomApiException(MENU_NOT_FOUND));
		Store foundStore = foundMenu.getStore();
		orderValidator.checkMinPrice(foundStore.getMinPrice(), foundMenu.getPrice(), request.getAmount());
		orderValidator.checkBusinessHours(foundStore.getOpenAt(), foundStore.getClosedAt());

		User foundUser = userRepository.findById(loginUserId)
				.orElseThrow(() -> new CustomApiException(USER_NOT_FOUND));

		Order createdOrder = Order.create(request, foundUser, foundMenu);
		Order savedOrder = orderRepository.save(createdOrder);

		return OrderAddRespDto.make(savedOrder);
	}

	@Transactional(readOnly = true)
	public OrderListForUserRespDto findAllByUserId(Long loginUserId, PageRequest pageRequest) {
		User foundUser = userRepository.findById(loginUserId)
				.orElseThrow(() -> new CustomApiException(USER_NOT_FOUND));

		Slice<Order> foundOrderList = orderRepository.findUserOrderHistory(foundUser.getUserId(), pageRequest);

		List<OrderFindForUserRespDto> responseOrders = foundOrderList.getContent().stream()
				.map(OrderFindForUserRespDto::make)
				.toList();

		return OrderListForUserRespDto.make(responseOrders, foundOrderList.getPageable());
	}

	@Transactional
	public void updateOrderStatus(Long loginUserId, OrderUpdateStatusReqDto request) {
		OrderStatus requestStatus = findByName(request.getStatus());

		Order foundOrder = orderRepository.findById(loginUserId)
				.orElseThrow(() -> new CustomApiException(ORDER_NOT_FOUND));
		if (foundOrder.getStatus().equals(CANCEL)) {
			throw new CustomApiException(ALREADY_CANCEL_ORDER);
		}

		User foundUser = userRepository.findById(loginUserId)
				.orElseThrow(() -> new CustomApiException(USER_NOT_FOUND));
		if (!foundUser.getUserId().equals(foundOrder.getMenu().getStore().getOwner().getUserId())) {
			throw new CustomApiException(NOT_STORE_OWNER);
		}

		foundOrder.updateStatus(requestStatus);
		orderRepository.save(foundOrder);
	}
}
