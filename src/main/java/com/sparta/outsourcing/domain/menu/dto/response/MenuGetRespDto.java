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
    private int price;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MenuGetRespDto toDto(Menu menu) {
        return new MenuGetRespDto(
                menu.getMenuId(),
                menu.getStore().getStoreId(),
                menu.getName(),
                menu.getPrice(),
                menu.isDeleted(),
                menu.getCreatedAt(),
                menu.getUpdatedAt()
        );
    }

}
