package com.sparta.outsourcing.domain.review.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.review.dto.request.ReviewCreateReqDto;
import com.sparta.outsourcing.domain.review.dto.request.ReviewPatchReqDto;
import com.sparta.outsourcing.domain.review.dto.request.ReviewGetReqDto;
import com.sparta.outsourcing.domain.review.dto.response.ReviewCreateRespDto;
import com.sparta.outsourcing.domain.review.dto.response.ReviewPatchRespDto;
import com.sparta.outsourcing.domain.review.entity.Review;
import com.sparta.outsourcing.domain.review.dto.response.ReviewGetRespDto;
import com.sparta.outsourcing.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

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

    public Page<ReviewGetRespDto> getStoreReviewsBetweenStar(Pageable pageable, Long storeId, ReviewGetReqDto reqDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));

        return reviewRepository
                .findByOrder_Menu_StoreAndStarBetween(store, reqDto.getMinStar(), reqDto.getMaxStar(), pageable)
                .map(ReviewGetRespDto::new);
    }

    public Page<ReviewGetRespDto> getStoreReviews(Pageable pageable, Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));

        return reviewRepository
                .findByOrder_Menu_Store(store, pageable)
                .map(ReviewGetRespDto::new);
    }

    public ReviewPatchRespDto updateReview(Long userId, Long reviewId, ReviewPatchReqDto reqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.REVIEW_NOT_FOUND));
        if(!Objects.equals(user.getUserId(), review.getOrder().getUser().getUserId())) {
            throw new CustomApiException(ErrorCode.NOT_ORDERED);
        }

        review.update(reqDto.getContent(), reqDto.getStar());
        return new ReviewPatchRespDto(reviewRepository.saveAndFlush(review));
    }

    public void deleteReview(Long userId, Long reviewId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.REVIEW_NOT_FOUND));
        if(!Objects.equals(user.getUserId(), review.getOrder().getUser().getUserId())) {
            throw new CustomApiException(ErrorCode.NOT_ORDERED);
        }

        reviewRepository.delete(review);
    }
}
