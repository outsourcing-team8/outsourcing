package com.sparta.outsourcing.domain.menu.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.common.security.LoginUser;
import com.sparta.outsourcing.domain.menu.dto.request.MenuCreateReqDto;
import com.sparta.outsourcing.domain.menu.dto.request.MenuPatchReqDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuCreateRespDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuDeleteRespDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuGetRespDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuPatchRespDto;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.entity.UserRole;
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

    @Transactional
    public MenuCreateRespDto createMenu(
            Long storeId, MenuCreateReqDto dto, LoginUser loginUser) {
        User user = loginUser.getUser();

        Store store = storeRepository.findById(storeId).orElseThrow(()
                -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));

        if (!user.getEmail().equals(store.getOwner().getEmail())) {
            throw new CustomApiException(ErrorCode.NOT_STORE_OWNER);
        }

        Menu menuByName = menuRepository.findByStoreStoreIdAndName(storeId, dto.getName());

        if (menuByName != null && menuByName.isDeleted()) {
            menuByName.deleted(false);
            menuByName.update(dto.getName(), dto.getPrice());
            return new MenuCreateRespDto(menuByName.getMenuId());
        }

        if (menuByName != null && menuByName.getName().equals(dto.getName())) {
            throw new CustomApiException(ErrorCode.DUPLICATE_MENU_NAME);
        }

        return new MenuCreateRespDto(menuRepository.save(dto.toEntity(store)).getMenuId());
    }

    @Transactional
    public MenuPatchRespDto patchMenu(
            Long storeId, Long menuId, MenuPatchReqDto dto,LoginUser loginUser) {
        User user = loginUser.getUser();

        Store store = storeRepository.findById(storeId).orElseThrow(()
                -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));

        Menu menu = menuRepository.findById(menuId).orElseThrow(()
                -> new CustomApiException(ErrorCode.MENU_NOT_FOUND));

        if (!user.getEmail().equals(store.getOwner().getEmail())) {
            throw new CustomApiException(ErrorCode.NOT_STORE_OWNER);
        }

        if (!Objects.equals(store.getStoreId(), menu.getStore().getStoreId())) {
            throw new CustomApiException(ErrorCode.STORE_NOT_OWN);
        }

        menu.update(dto.getName(), dto.getPrice());
        return new MenuPatchRespDto(menuId, dto);
    }

    @Transactional
    public MenuDeleteRespDto deleteMenu(
            Long menuId, Long storeId, LoginUser loginUser) {
        User user = loginUser.getUser();

        Store store = storeRepository.findById(storeId).orElseThrow(()
                -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));

        if (!user.getEmail().equals(store.getOwner().getEmail())) {
            throw new CustomApiException(ErrorCode.NOT_STORE_OWNER);
        }

        Menu menu = menuRepository.findById(menuId).orElseThrow(()
                -> new CustomApiException(ErrorCode.MENU_NOT_FOUND));

        if (!Objects.equals(store.getStoreId(), menu.getStore().getStoreId())) {
            throw new CustomApiException(ErrorCode.STORE_NOT_OWN);
        }


        menu.deleted(true);
        return new MenuDeleteRespDto(menu);
    }

    public List<MenuGetRespDto> getMenuList(Long storeId) {

        Store store = storeRepository.findById(storeId).orElseThrow(()
                -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));

        return menuRepository.findAllByStoreStoreId(storeId)
                .stream()
                .map(MenuGetRespDto::new)
                .filter(m -> !m.isDeleted())
                .toList();
    }
}
