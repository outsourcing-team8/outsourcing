package com.sparta.outsourcing.domain.menu.controller;

import com.sparta.outsourcing.domain.menu.dto.request.MenuCreateReqDto;
import com.sparta.outsourcing.domain.menu.dto.request.MenuPatchReqDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuCreateRespDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuGetRespDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuPatchRespDto;
import com.sparta.outsourcing.domain.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                .body(menuService.createMenu(storeId, dto));
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuPatchRespDto> patchMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody @Valid MenuPatchReqDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuService.patchMenu(storeId, menuId, dto));
    }

    @GetMapping()
    public ResponseEntity<List<MenuGetRespDto>> getMenuList(
            @PathVariable Long storeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuService.getMenuList(storeId));
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuGetRespDto> getMenu(
            @PathVariable Long menuId,
            @PathVariable Long storeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuService.getMenu(menuId, storeId));
    }


}
