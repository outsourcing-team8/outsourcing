package com.sparta.outsourcing.domain.menu.dto.request;

import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.store.entity.Store;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class MenuCreateReqDto {

    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "메뉴 가격을 입력해주세요.")
    @Range(min = 0, max = 500000, message = "메뉴 가격은 최대 50만원 까지 가능합니다.")
    private Integer price;

    public Menu toEntity(Store store) {
        return Menu.builder()
                .store(store)
                .name(name)
                .price(price)
                .build();
    }
}
