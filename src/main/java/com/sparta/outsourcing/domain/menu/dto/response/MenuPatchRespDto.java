package com.sparta.outsourcing.domain.menu.dto.response;


import com.sparta.outsourcing.domain.menu.dto.request.MenuPatchReqDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuPatchRespDto {
    private Long id;
    private String name;
    private Integer price;

    public MenuPatchRespDto(Long menuId, MenuPatchReqDto dto) {
        this.id = menuId;
        this.name = dto.getName();
        this.price = dto.getPrice();
    }
}
