package com.sparta.outsourcing.domain.store.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.store.dto.request.StoreCreateReqDto;
import com.sparta.outsourcing.domain.store.dto.request.StoreUpdateReqDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreCreateRespDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreUpdateRespDto;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.entity.UserRole;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

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
    public StoreUpdateRespDto updateStore(Long ownerId, Long storeId, StoreUpdateReqDto reqDto) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.STORE_NOT_FOUND));
        if(!Objects.equals(owner.getUserId(), store.getOwner().getUserId())) {
            throw new CustomApiException(ErrorCode.NOT_STORE_OWNER);
        }

        store.update(reqDto.getOpenAt(), reqDto.getClosedAt(), reqDto.getMinPrice());
        return new StoreUpdateRespDto(storeRepository.saveAndFlush(store));
    }
}
