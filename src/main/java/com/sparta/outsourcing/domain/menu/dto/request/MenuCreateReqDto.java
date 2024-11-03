package com.sparta.outsourcing.domain.menu.dto.request;

import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.store.entity.Store;
import lombok.Getter;

@Getter
public class MenuCreateReqDto {


    private String name;

    private int price;

    public Menu toEntity(Store store) {
        return Menu.builder()
                .store(store)
                .name(name)
                .price(price)
                .build();
    }
}
