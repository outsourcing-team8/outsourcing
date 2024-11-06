package com.sparta.outsourcing.domain.order.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.order.dto.request.OrderAddReqDto;
import com.sparta.outsourcing.domain.order.dto.request.OrderCancelReqDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderAddRespDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderFindForUserRespDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderListForUserRespDto;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.enums.PaymentMethod;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.order.validator.OrderValidator;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.sparta.outsourcing.common.exception.ErrorCode.*;
import static com.sparta.outsourcing.domain.order.enums.OrderStatus.CANCEL;
import static com.sparta.outsourcing.domain.order.enums.OrderStatus.PENDING;

@Slf4j
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
	public void cancelOrder(Long loginUserId, OrderCancelReqDto request) {
		Order foundOrder = orderRepository.findByIdEntityGraph(request.getOrderId())
				.orElseThrow(() -> new CustomApiException(ORDER_NOT_FOUND));

		Long orderUserId = foundOrder.getUser().getUserId();
		if (!orderUserId.equals(loginUserId)) {
			throw new CustomApiException(NOT_ORDER_USER);
		}

		if (!foundOrder.getStatus().equals(PENDING)) {
			throw new CustomApiException(CAN_NOT_CANCEL_ORDER);
		}

		log.info("요청 시각 = {}, 가게 ID = {}, 주문 ID = {}",
				LocalDateTime.now(), foundOrder.getMenu().getStore().getStoreId(), foundOrder.getOrderId());

		foundOrder.updateStatus(CANCEL);
		orderRepository.save(foundOrder);
	}
}
