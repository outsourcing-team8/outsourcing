package com.sparta.outsourcing.domain.menu.controller;

import com.sparta.outsourcing.common.security.LoginUser;
import com.sparta.outsourcing.domain.menu.dto.request.MenuCreateReqDto;
import com.sparta.outsourcing.domain.menu.dto.request.MenuPatchReqDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuCreateRespDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuDeleteRespDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuGetRespDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuPatchRespDto;
import com.sparta.outsourcing.domain.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @RequestBody @Valid MenuCreateReqDto dto,
            @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuService.createMenu(storeId, dto, loginUser));
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuPatchRespDto> patchMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody @Valid MenuPatchReqDto dto,
            @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuService.patchMenu(storeId, menuId, dto, loginUser));
    }

    @DeleteMapping("{menuId}")
    public ResponseEntity<MenuDeleteRespDto> deleteMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(menuService.deleteMenu(menuId, storeId, loginUser));

    }

    @GetMapping()
    public ResponseEntity<List<MenuGetRespDto>> getMenuList(
            @PathVariable Long storeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuService.getMenuList(storeId));
    }
}
