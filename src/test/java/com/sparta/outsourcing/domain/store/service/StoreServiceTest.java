package com.sparta.outsourcing.domain.store.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.store.dto.request.StoreCreateReqDto;
import com.sparta.outsourcing.domain.store.dto.request.StorePatchReqDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreCreateRespDto;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private StoreService storeService;

    @Mock
    User owner;

    Long ownerId = 1L;
    Long storeId = 1L;

    String name = "치킨집";
    LocalTime openAt = LocalTime.of(14, 0);
    LocalTime closeAt = LocalTime.of(0, 0);
    int minPrice = 10000;

    Store store = Store.builder()
            .owner(owner)
            .name(name)
            .openAt(openAt)
            .closedAt(closeAt)
            .minPrice(minPrice)
            .build();

    @Nested
    @DisplayName("createStore Test")
    class createStoreTest {
        StoreCreateReqDto reqDto = new StoreCreateReqDto(name,openAt,closeAt,minPrice);

        @Test
        @DisplayName("가게 생성 정상 작동")
        void test1() {
            //given
            when(userRepository.findById(ownerId)).thenReturn(Optional.of(owner));
            when(storeRepository.countByOwnerAndDeletedIsFalse(any())).thenReturn(0);
            when(storeRepository.findByName(any())).thenReturn(Optional.empty());
            when(storeRepository.save(any())).thenReturn(reqDto.toEntity(owner));

            //when
            StoreCreateRespDto respDto = storeService.createStore(ownerId, reqDto);

            // then
            assertNotNull(respDto);
            assertEquals(reqDto.getName(), respDto.getName());
            assertEquals(reqDto.getOpenAt(), respDto.getOpenAt());
            assertEquals(reqDto.getClosedAt(), respDto.getClosedAt());
            assertEquals(reqDto.getMinPrice(), respDto.getMinPrice());
        }

        @Test
        @DisplayName("회원이 이미 3개의 가게를 운영중인 경우")
        void test2() {
            //given
            when(userRepository.findById(ownerId)).thenReturn(Optional.of(owner));
            when(storeRepository.countByOwnerAndDeletedIsFalse(any())).thenReturn(3);

            //when
            CustomApiException exception = assertThrows(CustomApiException.class,
                    () -> storeService.createStore(ownerId, reqDto));

            //then
            assertEquals(exception.getErrorCode(), ErrorCode.TOO_MANY_STORES);
        }

        @Test
        @DisplayName("이미 같은 이름의 가게가 영업중인 경우")
        void test3() {
            //given
            when(userRepository.findById(ownerId)).thenReturn(Optional.of(owner));
            when(storeRepository.countByOwnerAndDeletedIsFalse(any())).thenReturn(0);
            when(storeRepository.findByName(any())).thenReturn(Optional.of(store));

            //when
            CustomApiException exception = assertThrows(CustomApiException.class,
                    () -> storeService.createStore(ownerId, reqDto));

            //then
            assertEquals(exception.getErrorCode(), ErrorCode.ALREADY_STORE_EXIST);
        }
    }
}
