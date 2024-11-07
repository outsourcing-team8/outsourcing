package com.sparta.outsourcing.domain.review.dto.request;

import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.review.entity.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
@AllArgsConstructor
public class ReviewCreateReqDto {

    @NotBlank(message = "후기를 적어주세요.")
    @Size(min = 1, max = 255, message = "후기는 최소 1글자, 최대 255글자만 작성 가능합니다.")
    private String content;

    @NotNull(message = "별점을 입력해주세요.")
    @Range(min = 1, max = 5, message = "별점은 최소 1개, 최대 5개만 가능합니다.")
    private int star;

    public Review toEntity(Order order) {
        return Review.builder()
                .order(order)
                .content(this.content)
                .star(this.star)
                .build();
    }
}
