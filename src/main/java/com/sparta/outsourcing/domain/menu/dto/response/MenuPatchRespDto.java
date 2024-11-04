package com.sparta.outsourcing.domain.menu.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuPatchRespDto {
    private Long id;
    private String name;
    private int price;
}
