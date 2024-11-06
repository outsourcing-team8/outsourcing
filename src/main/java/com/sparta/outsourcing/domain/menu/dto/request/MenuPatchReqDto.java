package com.sparta.outsourcing.domain.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@AllArgsConstructor
@Builder
public class MenuPatchReqDto {

    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    private String name;

    @NotNull(message = "메뉴 가격을 입력해주세요.")
    @Range(min = 0, max = 500000, message = "메뉴 가격은 최대 50만원 까지 가능합니다.")
    private Integer price;


}
