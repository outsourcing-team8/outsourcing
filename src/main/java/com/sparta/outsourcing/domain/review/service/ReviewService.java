package com.sparta.outsourcing.domain.review.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.review.dto.request.ReviewCreateReqDto;
import com.sparta.outsourcing.domain.review.dto.response.ReviewCreateRespDto;
import com.sparta.outsourcing.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public ReviewCreateRespDto createReview(Long userId, Long orderId, ReviewCreateReqDto reqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.ORDER_NOT_FOUND));
        if(!Objects.equals(user.getUserId(), order.getUser().getUserId())) {
            throw new CustomApiException(ErrorCode.NOT_ORDERED);
        }
        reviewRepository.findByOrder(order)
                .ifPresent(review -> {throw new CustomApiException(ErrorCode.ALREADY_REVIEWED_ORDER);});

        return new ReviewCreateRespDto(reviewRepository.save(reqDto.toEntity(order)));
    }
}
