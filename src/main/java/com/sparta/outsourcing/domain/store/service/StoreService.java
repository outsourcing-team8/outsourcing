package com.sparta.outsourcing.domain.store.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.store.dto.request.StoreCreateReqDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreCreateRespDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreOneGetRespDto;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;
import com.sparta.outsourcing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    public StoreCreateRespDto createStore(User owner, StoreCreateReqDto reqDto) {
        if(storeRepository.countByOwnerAndDeletedIsFalse(owner) >= 3) {
            throw new CustomApiException(ErrorCode.TOO_MANY_STORES);
        }
        storeRepository.findByName(reqDto.getName())
                .ifPresent(store -> {throw new CustomApiException(ErrorCode.ALREADY_STORE_EXIST);});

        return new StoreCreateRespDto(storeRepository.save(reqDto.toEntity(owner)));
    }

    public StoreOneGetRespDto getOneStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));
        List<Menu> menuList = menuRepository.findAllByStore(store);
        return new StoreOneGetRespDto(store, menuList);
    }
}
