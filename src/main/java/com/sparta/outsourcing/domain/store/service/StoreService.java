package com.sparta.outsourcing.domain.store.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.store.dto.request.StoreCreateReqDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreCreateRespDto;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;
import com.sparta.outsourcing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreCreateRespDto createStore(User owner, StoreCreateReqDto reqDto) {
        if(storeRepository.countByUser(owner) >= 3) {
            throw new CustomApiException(ErrorCode.TOO_MANY_STORES);
        }
        storeRepository.findByName(reqDto.getName())
                .ifPresent(store -> {throw new CustomApiException(ErrorCode.ALREADY_STORE_EXIST);});

        return new StoreCreateRespDto(storeRepository.save(reqDto.toEntity(owner)));
    }
}
