package com.sparta.outsourcing.domain.review.service;

import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.review.dto.request.ReviewCreateReqDto;
import com.sparta.outsourcing.domain.review.dto.response.ReviewCreateRespDto;
import com.sparta.outsourcing.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class) // @Mock 사용을 위해 설정합니다.
class ReviewServiceTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    User user;

    @Mock
    Order order;

    @Test
    @DisplayName("리뷰 생성 api 테스트")
    void createReview() {
        Long userId = 1L;
        Long orderId = 1L;

        ReviewCreateReqDto reqDto = new ReviewCreateReqDto("good", 3);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(user.getUserId()).willReturn(userId);
        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));
        given(order.getUser()).willReturn(user);
        given(reviewRepository.save(any())).willReturn(reqDto.toEntity(order));

        ReviewService reviewService = new ReviewService(reviewRepository, orderRepository, userRepository);

        ReviewCreateRespDto respDto = reviewService.createReview(userId, orderId, reqDto);
        assertNotNull(respDto);
    }
}
