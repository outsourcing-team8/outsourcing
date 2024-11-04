package com.sparta.outsourcing.domain.store.controller;

import com.sparta.outsourcing.domain.store.dto.request.StoreCreateReqDto;
import com.sparta.outsourcing.domain.store.dto.request.StoreUpdateReqDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreCreateRespDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreUpdateRespDto;
import com.sparta.outsourcing.domain.store.service.StoreService;
import com.sparta.outsourcing.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<StoreCreateRespDto> createStore(@AuthenticationPrincipal User user, @RequestBody @Valid StoreCreateReqDto reqDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.createStore(user, reqDto));
    }

    @PatchMapping("/{storeId}")
    public ResponseEntity<StoreUpdateRespDto> updateStore(@AuthenticationPrincipal User user, @PathVariable Long storeId, @RequestBody @Valid StoreUpdateReqDto reqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(storeService.updateStore(user, storeId, reqDto));
    }
}
