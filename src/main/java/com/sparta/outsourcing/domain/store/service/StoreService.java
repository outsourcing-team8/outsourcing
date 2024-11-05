package com.sparta.outsourcing.domain.store.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.store.dto.request.StoreCreateReqDto;
import com.sparta.outsourcing.domain.store.dto.request.StoreGetReqDto;
import com.sparta.outsourcing.domain.store.dto.request.StorePatchReqDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreCreateRespDto;
import com.sparta.outsourcing.domain.store.dto.response.StorePatchRespDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreOneGetRespDto;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.dto.response.StoreGetRespDto;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    public StoreCreateRespDto createStore(Long ownerId, StoreCreateReqDto reqDto) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));
        if(storeRepository.countByOwnerAndDeletedIsFalse(owner) >= 3) {
            throw new CustomApiException(ErrorCode.TOO_MANY_STORES);
        }
        storeRepository.findByName(reqDto.getName())
                .ifPresent(store -> {throw new CustomApiException(ErrorCode.ALREADY_STORE_EXIST);});

        return new StoreCreateRespDto(storeRepository.save(reqDto.toEntity(owner)));
    }

    @Transactional
    public StorePatchRespDto updateStore(Long ownerId, Long storeId, StorePatchReqDto reqDto) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));
        if(!Objects.equals(owner.getUserId(), store.getOwner().getUserId())) {
            throw new CustomApiException(ErrorCode.NOT_STORE_OWNER);
        }

        store.update(reqDto.getOpenAt(), reqDto.getClosedAt(), reqDto.getMinPrice());
        return new StorePatchRespDto(storeRepository.saveAndFlush(store));
    }

    public Page<StoreGetRespDto> getStores(int page, int size, StoreGetReqDto reqDto) {
        Pageable pageable = PageRequest.of(page, size);
        String name = reqDto.getName();
        if(name == null ||  name.isEmpty()) return storeRepository.findAllByDeletedIsFalse(pageable).map(StoreGetRespDto::new);
        return storeRepository.findAllByDeletedIsFalseAndNameContaining(name, pageable).map(StoreGetRespDto::new);
    }

    public StoreOneGetRespDto getOneStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));
        List<Menu> menuList = menuRepository.findAllByStoreStoreId(storeId);
        return new StoreOneGetRespDto(store, menuList);
    }
}
