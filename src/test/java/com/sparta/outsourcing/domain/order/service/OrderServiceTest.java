package com.sparta.outsourcing.domain.order.service;

import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.order.dto.request.OrderAddReqDto;
import com.sparta.outsourcing.domain.order.dto.response.OrderAddRespDto;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.order.validator.OrderValidator;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;
import com.sparta.outsourcing.domain.user.entity.Address;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.entity.UserRole;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock private OrderRepository orderRepository;
    @Mock private UserRepository userRepository;
    @Mock private MenuRepository menuRepository;
    @Mock private StoreRepository storeRepository;
    @Mock private OrderValidator orderValidator;

    @InjectMocks private OrderService orderService;

    @Test
    @DisplayName("주문 생성 기능 정상 작동 확인")
    void addTest() {
        // given
        Long customerId = 1L;
        Address address = new Address();
        address.setStreet("도로명 주소");
        address.setDetails("동호수");
        address.setPostCode("우편 번호");
        User customer = User.builder()
                .userId(customerId)
                .email("customer@test.com")
                .password("123456")
                .nickname("고객")
                .role(UserRole.USER)
                .phoneNumber("010-0000-0000")
                .address(address)
                .build();

        User owner = User.builder()
                .userId(2L)
                .email("owner@test.com")
                .password("123456")
                .nickname("사장")
                .role(UserRole.OWNER)
                .phoneNumber("010-0000-0000")
                .address(address)
                .build();

        LocalTime openAt = LocalTime.of(9, 0);
        LocalTime closeAt = LocalTime.of(21, 0);
        Store testStore = new Store(1L, owner, "김사장네", openAt, closeAt, 20000);

        Menu testMenu = new Menu(testStore, "삼겹살", 13000, false);

        when(menuRepository.findById(any())).thenReturn(Optional.of(testMenu));
        when(userRepository.findById(any())).thenReturn(Optional.of(customer));

        OrderAddReqDto request = OrderAddReqDto.builder()
                .menuId(testMenu.getMenuId())
                .amount(2)
                .requestMessage("요청 메시지")
                .paymentMethod("CASH")
                .build();
        Order order = Order.builder()
                .orderId(1L)
                .amount(request.getAmount())
                .deliveryAddress("배달 주소")
                .customerPhoneNumber(customer.getPhoneNumber())
                .requestMessage(request.getRequestMessage())
                .paymentMethod(request.getPaymentMethod())
                .user(customer)
                .menu(testMenu)
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // when
        OrderAddRespDto orderAddRespDto = orderService.add(request, customerId);

        // then
        assertThat(orderAddRespDto).isNotNull();
        assertThat(orderAddRespDto.getOrderId()).isEqualTo(1L);
    }
}