package com.sparta.outsourcing.domain.menu.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuPatchRespDto {
    private Long id;
    private String name;
    private int price;
}
