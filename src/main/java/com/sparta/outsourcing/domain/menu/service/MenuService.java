package com.sparta.outsourcing.domain.menu.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.menu.dto.request.MenuCreateReqDto;
import com.sparta.outsourcing.domain.menu.dto.request.MenuPatchReqDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuCreateRespDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuGetRespDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuPatchRespDto;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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

    @Transactional
    public MenuPatchRespDto patchMenu(
            Long storeId, Long menuId, MenuPatchReqDto dto) {
        Store store = storeRepository.findById(storeId).orElseThrow(()
                -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));

        Menu menu = menuRepository.findById(menuId).orElseThrow(()
                -> new CustomApiException(ErrorCode.MENU_NOT_FOUND));

        if (!Objects.equals(store.getStoreId(), menu.getStore().getStoreId())) {
            throw new CustomApiException(ErrorCode.STORE_NOT_FOUND);
        }

        dto.toEntity(store);
        return dto.toDto(menuId, dto);
    }


    public List<MenuGetRespDto> getMuenuList(Long storeId) {
        storeRepository.findById(storeId).orElseThrow(()
                -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));
        List<Menu> menus = menuRepository.findAllByStoreId(storeId);

        if (menus == null || menus.isEmpty()) {
            throw new CustomApiException(ErrorCode.MENU_NOT_FOUND);
        }
        return menus.stream().map(MenuGetRespDto::toDto).toList();
    }

    public MenuGetRespDto getMuenu(Long menuId, Long storeId) {
        storeRepository.findById(storeId).orElseThrow(()
                -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));

        Menu menu = menuRepository.findById(menuId).orElseThrow(()
                -> new CustomApiException(ErrorCode.MENU_NOT_FOUND));

        if (menu == null) {
            throw new CustomApiException(ErrorCode.MENU_NOT_FOUND);
        }
        return MenuGetRespDto.toDto(menu);
    }
}
