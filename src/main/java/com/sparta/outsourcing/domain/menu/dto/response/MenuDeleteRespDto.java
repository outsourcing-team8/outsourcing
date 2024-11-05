package com.sparta.outsourcing.domain.menu.dto.response;

import com.sparta.outsourcing.domain.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuDeleteRespDto {
    private Long menuId;
    private boolean deleted;

    public MenuDeleteRespDto(Menu menu) {
        this.menuId = menu.getMenuId();
        this.deleted = menu.isDeleted();
    }

}
