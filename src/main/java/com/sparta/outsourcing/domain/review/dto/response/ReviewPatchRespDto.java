package com.sparta.outsourcing.domain.review.dto.response;

import com.sparta.outsourcing.domain.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewPatchRespDto {
    private final Long reviewId;
    private final Long orderId;
    private final String content;
    private final int star;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ReviewPatchRespDto(Review review) {
        this.reviewId = review.getReviewId();
        this.orderId = review.getOrder().getOrderId();
        this.content = review.getContent();
        this.star = review.getStar();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
    }
}
