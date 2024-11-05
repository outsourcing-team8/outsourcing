package com.sparta.outsourcing.domain.menu.dto.response;

import com.sparta.outsourcing.domain.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MenuGetRespDto {
    private Long id;
    private Long storeId;
    private String name;
    private Integer price;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public MenuGetRespDto(Menu menu) {
        this.id = menu.getMenuId();
        this.storeId = menu.getStore().getStoreId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.deleted = menu.isDeleted();
        this.createdAt = menu.getCreatedAt();
        this.updatedAt = menu.getUpdatedAt();
    }

}
