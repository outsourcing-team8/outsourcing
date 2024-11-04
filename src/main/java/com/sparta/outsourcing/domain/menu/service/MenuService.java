package com.sparta.outsourcing.domain.menu.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.menu.dto.request.MenuCreateReqDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuCreateRespDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuPatchRespDto;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuCreateRespDto createMenu(
            Long storeId, MenuCreateReqDto dto) {
        Store store = storeRepository.findById(storeId).orElseThrow(()
                -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));
        return new MenuCreateRespDto(menuRepository.save(dto.toEntity(store)).getMenuId());
    }

}
