package com.sparta.outsourcing.domain.store.dto.response;

import com.sparta.outsourcing.domain.menu.entity.Menu;
import lombok.Getter;

@Getter
public class StoreMenuGetRespDto {
    private final Long menuId;
    private final String name;
    private final int price;

    public StoreMenuGetRespDto(Menu menu) {
        this.menuId = menu.getMenuId();
        this.name = menu.getName();
        this.price = menu.getPrice();
    }
}
