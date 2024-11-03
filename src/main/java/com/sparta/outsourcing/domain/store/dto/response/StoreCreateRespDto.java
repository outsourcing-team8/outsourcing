package com.sparta.outsourcing.domain.store.dto.response;

import com.sparta.outsourcing.domain.store.entity.Store;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class StoreCreateRespDto {

    private final Long storeId;
    private final String name;
    private final LocalTime openAt;
    private final LocalTime closedAt;
    private final int minPrice;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public StoreCreateRespDto(Store store) {
        this.storeId = store.getStoreId();
        this.name = store.getName();
        this.openAt = store.getOpenAt();
        this.closedAt = store.getClosedAt();
        this.minPrice = store.getMinPrice();
        this.createdAt = store.getCreatedAt();
        this.updatedAt = store.getUpdatedAt();
    }
}
