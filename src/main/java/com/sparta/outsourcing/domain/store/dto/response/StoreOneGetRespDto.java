package com.sparta.outsourcing.domain.store.dto.response;

import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.store.entity.Store;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class StoreOneGetRespDto {
    private final Long storeId;
    private final String name;
    private final LocalTime openAt;
    private final LocalTime closedAt;
    private final List<StoreMenuGetRespDto> menuList;

    public StoreOneGetRespDto(Store store, List<Menu> menuList) {
        this.storeId = store.getStoreId();
        this.name = store.getName();
        this.openAt = store.getOpenAt();
        this.closedAt = store.getClosedAt();
        this.menuList = menuList.stream().map(StoreMenuGetRespDto::new).toList();
    }
}
