package com.sparta.outsourcing.domain.review.dto.request;

import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.review.entity.Review;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Getter
public class ReviewCreateReqDto {

    @Length(min = 1, max = 255)
    private String content;
    @Range(min = 1, max = 5)
    private int star;

    public Review toEntity(Order order) {
        return Review.builder()
                .order(order)
                .content(this.content)
                .star(this.star)
                .build();
    }
}
