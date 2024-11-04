package com.sparta.outsourcing.domain.menu.controller;

import com.sparta.outsourcing.domain.menu.dto.request.MenuPatchReqDto;
import com.sparta.outsourcing.domain.menu.dto.request.MenuCreateReqDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuCreateRespDto;
import com.sparta.outsourcing.domain.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores/{storeId}")
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/menu")
    public ResponseEntity<MenuCreateRespDto> createMenu(
            @PathVariable Long storeId,
            @RequestBody @Valid MenuCreateReqDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuService.createMenu(storeId,dto));
    }

    @PatchMapping("/menu/{menuId}")
    public ResponseEntity<MenuCreateRespDto> updateMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody @Valid MenuPatchReqDto dto){

        return null;
    }

}
