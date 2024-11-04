package com.sparta.outsourcing.domain.menu.dto.response;

import com.sparta.outsourcing.domain.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuDeleteRespDto {
    private Long menuId;
    private boolean deleted;

    public static MenuDeleteRespDto toDto(Menu menu) {
        return new MenuDeleteRespDto(
                menu.getMenuId(),
                menu.isDeleted()
        );
    }

    public static Menu toEntity() {
    return Menu.builder()
            .deleted(true)
            .build();
    }
}
