package com.sparta.outsourcing.domain.store.dto.response;

import com.sparta.outsourcing.domain.store.entity.Store;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreGetRespDto {
    private final Long storeId;
    private final String name;
    private final LocalTime openAt;
    private final LocalTime closedAt;
    private final int minPrice;

    public StoreGetRespDto(Store store) {
        this.storeId = store.getStoreId();
        this.name = store.getName();
        this.openAt = store.getOpenAt();
        this.closedAt = store.getClosedAt();
        this.minPrice = store.getMinPrice();
    }
}
