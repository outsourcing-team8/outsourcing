package com.sparta.outsourcing.domain.review.dto.request;

import com.sparta.outsourcing.domain.review.annotation.StarRange;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@StarRange
public class ReviewGetReqDto {

    @NotNull(message = "최소 별점을 입력해주세요.")
    @Min(1)
    private Integer minStar;

    @NotNull(message = "최대 별점을 입력해주세요.")
    @Max(5)
    private Integer maxStar;
}
