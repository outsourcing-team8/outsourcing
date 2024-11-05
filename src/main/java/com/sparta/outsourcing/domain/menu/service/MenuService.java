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
            Long storeId, MenuCreateReqDto dto,
            LoginUser loginUser) throws CustomApiException {
        if (loginUser.getUser().getRole() != UserRole.OWNER) {
            throw new CustomApiException(ErrorCode.NO_AUTHORITY);
        }

        Store store = storeRepository.findById(storeId).orElseThrow(()
                -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));

        return new MenuCreateRespDto(menuRepository.save(dto.toEntity(store)).getMenuId());
    }

    @Transactional
    public MenuPatchRespDto patchMenu(
            Long storeId, Long menuId, MenuPatchReqDto dto,
            LoginUser loginUser) {
        if (loginUser.getUser().getRole() != UserRole.OWNER) {
            throw new CustomApiException(ErrorCode.NO_AUTHORITY);
        }

        Store store = storeRepository.findById(storeId).orElseThrow(()
                -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));

        Menu menu = menuRepository.findById(menuId).orElseThrow(()
                -> new CustomApiException(ErrorCode.MENU_NOT_FOUND));

        if (!Objects.equals(store.getStoreId(), menu.getStore().getStoreId())) {
            throw new CustomApiException(ErrorCode.STORE_NOT_FOUND);
        }

        menu.update(dto.getName(), dto.getPrice());
        return new MenuPatchRespDto(menuId, dto);
    }

    @Transactional
    public MenuDeleteRespDto deleteMenu(Long menuId, Long storeId, LoginUser loginUser) {
        if (loginUser.getUser().getRole() != UserRole.OWNER) {
            throw new CustomApiException(ErrorCode.NO_AUTHORITY);
        }

        Menu menu = check(menuId, storeId);
        menu.deleted();
        return new MenuDeleteRespDto(menu);
    }

    public List<MenuGetRespDto> getMenuList(Long storeId) {
        storeRepository.findById(storeId).orElseThrow(()
                -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));

        List<Menu> menus = menuRepository.findAllByStoreStoreId(storeId);

        return menus.stream().map(MenuGetRespDto::new).toList();
    }


    public MenuGetRespDto getMenu(Long menuId, Long storeId) {
        Menu menu = check(menuId, storeId);
        return new MenuGetRespDto(menu);
    }


    private Menu check(Long menuId, Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(()
                -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));

        Menu menu = menuRepository.findById(menuId).orElseThrow(()
                -> new CustomApiException(ErrorCode.MENU_NOT_FOUND));

        if (!Objects.equals(store.getStoreId(), menu.getStore().getStoreId())) {
            throw new CustomApiException(ErrorCode.STORE_NOT_OWN);
        }
        return menu;
    }

}
