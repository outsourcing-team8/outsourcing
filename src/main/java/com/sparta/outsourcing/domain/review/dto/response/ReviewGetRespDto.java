package com.sparta.outsourcing.domain.review.dto.response;

import com.sparta.outsourcing.domain.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewGetRespDto {

    private final Long reviewId;
    private final String storeName;
    private final String menuName;
    private final LocalDateTime orderAt;
    private final String content;
    private final int star;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ReviewGetRespDto(Review review) {
        this.reviewId = review.getReviewId();
        this.storeName = review.getOrder().getMenu().getStore().getName();
        this.menuName = review.getOrder().getMenu().getName();
        this.orderAt = review.getOrder().getCreatedAt();
        this.content = review.getContent();
        this.star = review.getStar();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
    }
}
