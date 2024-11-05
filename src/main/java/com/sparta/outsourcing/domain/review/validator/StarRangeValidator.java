package com.sparta.outsourcing.domain.review.validator;

import com.sparta.outsourcing.domain.review.annotation.StarRange;
import com.sparta.outsourcing.domain.review.dto.request.ReviewGetReqDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StarRangeValidator implements ConstraintValidator<StarRange, ReviewGetReqDto> {

    @Override
    public void initialize(StarRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(ReviewGetReqDto reqDto, ConstraintValidatorContext context) {
        return reqDto.getMinStar() <= reqDto.getMaxStar();
    }

}
