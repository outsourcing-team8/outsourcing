package com.sparta.outsourcing.domain.order.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.order.convertor.LocalDateTimeConvertor;
import com.sparta.outsourcing.domain.order.dto.request.OrderAddReqDto;
import com.sparta.outsourcing.domain.order.dto.response.*;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.enums.PaymentMethod;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.order.validator.OrderValidator;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final MenuRepository menuRepository;
	private final UserRepository userRepository;
	private final OrderValidator orderValidator;
	private final StoreRepository storeRepository;
	private final LocalDateTimeConvertor localDateTimeConvertor;

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

	@Transactional(readOnly = true)
	public OrderListForOwnerRespDto findAllByOwnerId(Long loginUserId, Long storeId,
													 String selectedDate, PageRequest pageRequest) {
		LocalDateTime convertedDate = localDateTimeConvertor.convertStringToLocalDateTime(selectedDate);

		Store foundStore = storeRepository.findById(storeId)
				.orElseThrow(() -> new CustomApiException(STORE_NOT_FOUND));

		if (!foundStore.getOwner().getUserId().equals(loginUserId)) {
			throw new CustomApiException(NOT_STORE_OWNER);
		}

		Slice<Order> foundOrderList = orderRepository.findStoreOrderList(storeId, convertedDate, pageRequest);

		List<OrderFindForOwnerRespDto> responseOrders = foundOrderList.getContent().stream()
				.map(OrderFindForOwnerRespDto::make)
				.toList();

		return OrderListForOwnerRespDto.make(responseOrders, foundOrderList.getPageable());
	}
}
