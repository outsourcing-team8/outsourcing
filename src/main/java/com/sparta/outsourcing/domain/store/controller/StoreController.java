package com.sparta.outsourcing.domain.store.controller;

import com.sparta.outsourcing.common.security.LoginUser;
import com.sparta.outsourcing.domain.store.dto.request.StoreCreateReqDto;
import com.sparta.outsourcing.domain.store.dto.request.StoreUpdateReqDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreCreateRespDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreUpdateRespDto;
import com.sparta.outsourcing.domain.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    @Secured("ROLE_OWNER")
    @PostMapping
    public ResponseEntity<StoreCreateRespDto> createStore(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody @Valid StoreCreateReqDto reqDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(storeService.createStore(loginUser.getUser().getUserId(), reqDto));
    }

    @Secured("ROLE_OWNER")
    @PatchMapping("/{storeId}")
    public ResponseEntity<StoreUpdateRespDto> updateStore(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long storeId,
            @RequestBody @Valid StoreUpdateReqDto reqDto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeService.updateStore(loginUser.getUser().getUserId(), storeId, reqDto));
    }
}
