package com.sparta.outsourcing.domain.menu.service;

import com.sparta.outsourcing.domain.menu.dto.request.MenuCreateReqDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuCreateRespDto;
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
            Long storeId, MenuCreateReqDto menuCreateReqDto) {
            Store store = storeRepository.findById(storeId).orElseThrow(()
                    -> new IllegalArgumentException("Store not found"));
            return new MenuCreateRespDto(menuRepository.save(menuCreateReqDto.toEntity(store)).getMenuId());
    }
}
