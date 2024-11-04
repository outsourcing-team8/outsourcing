package com.sparta.outsourcing.domain.menu.controller;

import com.sparta.outsourcing.domain.menu.dto.request.MenuCreateReqDto;
import com.sparta.outsourcing.domain.menu.dto.request.MenuPatchReqDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuCreateRespDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuPatchRespDto;
import com.sparta.outsourcing.domain.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores/{storeId}/menus")
public class MenuController {
    private final MenuService menuService;

    @PostMapping()
    public ResponseEntity<MenuCreateRespDto> createMenu(
            @PathVariable Long storeId,
            @RequestBody @Valid MenuCreateReqDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuService.createMenu(storeId,dto));
    }

    @PatchMapping("/menu/{menuId}")
    public ResponseEntity<MenuPatchRespDto> patchMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody @Valid MenuPatchReqDto dto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuService.patchMenu(storeId,menuId,dto));
    }

}
