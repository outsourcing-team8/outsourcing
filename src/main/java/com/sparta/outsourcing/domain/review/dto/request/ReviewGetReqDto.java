package com.sparta.outsourcing.domain.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class ReviewGetReqDto {

    @Min(1)
    private Integer minStar;

    @Max(5)
    private Integer maxStar;
}
