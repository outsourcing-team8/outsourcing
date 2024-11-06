package com.sparta.outsourcing.domain.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class ReviewPatchReqDto {

    @NotBlank(message = "후기를 적어주세요.")
    @Size(max = 255, message = "최대 255자 가지만 작성 가능합니다.")
    private String content;

    @NotNull(message = "별점을 입력해주세요.")
    @Range(min = 1, max = 5, message = "별점은 최소 1개, 최대 5개 까지만 가능합니다.")
    private Integer star;
}
