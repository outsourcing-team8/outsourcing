package com.sparta.outsourcing.domain.store.controller;

import com.sparta.outsourcing.common.security.LoginUser;
import com.sparta.outsourcing.domain.store.dto.request.StoreCreateReqDto;
import com.sparta.outsourcing.domain.store.dto.request.StoreGetReqDto;
import com.sparta.outsourcing.domain.store.dto.request.StorePatchReqDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreCreateRespDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreGetRespDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreOneGetRespDto;
import com.sparta.outsourcing.domain.store.dto.response.StorePatchRespDto;
import com.sparta.outsourcing.domain.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<StoreCreateRespDto> createStore(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody @Valid StoreCreateReqDto reqDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(storeService.createStore(loginUser.getUser().getUserId(), reqDto));
    }

    @PatchMapping("/{storeId}")
    public ResponseEntity<StorePatchRespDto> updateStore(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long storeId,
            @RequestBody @Valid StorePatchReqDto reqDto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeService.patchStore(loginUser.getUser().getUserId(), storeId, reqDto));
    }

    @GetMapping
    public ResponseEntity<Page<StoreGetRespDto>> getStores(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestBody StoreGetReqDto reqDto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeService.getStores(pageable, reqDto));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreOneGetRespDto> getOneStore(@PathVariable Long storeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeService.getOneStore(storeId));
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long storeId
    ) {
        storeService.deleteStore(loginUser.getUser().getUserId(), storeId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
