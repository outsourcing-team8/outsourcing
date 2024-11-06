package com.sparta.outsourcing.domain.store.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.store.dto.request.StoreCreateReqDto;
import com.sparta.outsourcing.domain.store.dto.request.StorePatchReqDto;
import com.sparta.outsourcing.domain.store.dto.response.StoreCreateRespDto;
import com.sparta.outsourcing.domain.store.dto.response.StorePatchRespDto;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StoreService storeService;

    Long ownerId = 1L;
    User owner = User.builder().userId(ownerId).build();

    Long storeId = 1L;
    String name = "치킨집";
    LocalTime openAt = LocalTime.of(14, 0);
    LocalTime closeAt = LocalTime.of(0, 0);
    int minPrice = 10000;
    Store store = Store.builder()
            .storeId(storeId)
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

            //when & then
            CustomApiException exception = assertThrows(CustomApiException.class,
                    () -> storeService.createStore(ownerId, reqDto));
            assertEquals(exception.getErrorCode(), ErrorCode.ALREADY_STORE_EXIST);
        }
    }

    @Nested
    @DisplayName("patchStore Test")
    class patchStoreTest {
        StorePatchReqDto reqDto = new StorePatchReqDto(
                LocalTime.of(11, 30),
                LocalTime.of(23, 30),
                13000
        );

        @Test
        @DisplayName("가게 정보 수정 정상 작동")
        void test1() {
            // given
            when(userRepository.findById(ownerId)).thenReturn(Optional.of(owner));
            when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
            when(storeRepository.saveAndFlush(any())).thenReturn(store);

            //when
            StorePatchRespDto respDto = storeService.patchStore(ownerId, storeId, reqDto);

            //then
            assertNotNull(respDto);
            assertEquals(reqDto.getOpenAt(), respDto.getOpenAt());
            assertEquals(reqDto.getClosedAt(), respDto.getClosedAt());
            assertEquals(reqDto.getMinPrice(), respDto.getMinPrice());
        }

        @Test
        @DisplayName("잘못된 storeId를 입력한 경우")
        void test2() {
            //given
            when(userRepository.findById(ownerId)).thenReturn(Optional.of(owner));
            when(storeRepository.findById(storeId)).thenReturn(Optional.empty());

            //when & then
            CustomApiException exception = assertThrows(CustomApiException.class,
                    () -> storeService.patchStore(ownerId, storeId, reqDto));
            assertEquals(exception.getErrorCode(), ErrorCode.STORE_NOT_FOUND);
        }

        @Test
        @DisplayName("해당 가게의 사장이 아닌 경우")
        void test3() {
            //given
            User otherOwner = User.builder().userId(2L).build();
            when(userRepository.findById(ownerId)).thenReturn(Optional.of(otherOwner));
            when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

            //when & then
            CustomApiException exception = assertThrows(CustomApiException.class,
                    () -> storeService.patchStore(ownerId, storeId, reqDto));
            assertEquals(exception.getErrorCode(), ErrorCode.NOT_STORE_OWNER);
        }
    }

    @Nested
    @DisplayName("deleteStore Test")
    class deleteStoreTest {
        @Test
        @DisplayName("잘못된 storeId를 입력한 경우")
        void test1() {
            //given
            when(userRepository.findById(ownerId)).thenReturn(Optional.of(owner));
            when(storeRepository.findByStoreIdAndDeletedIsFalse(storeId)).thenReturn(Optional.empty());

            //when & then
            CustomApiException exception = assertThrows(CustomApiException.class,
                    () -> storeService.deleteStore(ownerId, storeId));
            assertEquals(exception.getErrorCode(), ErrorCode.STORE_NOT_FOUND);
        }


        @Test
        @DisplayName("해당 가게의 사장이 아닌 경우")
        void test2() {
            //given
            when(userRepository.findById(2L)).thenReturn(Optional.of(owner));
            when(storeRepository.findByStoreIdAndDeletedIsFalse(storeId)).thenReturn(Optional.of(store));

            //when & then
            CustomApiException exception = assertThrows(CustomApiException.class,
                    () -> storeService.deleteStore(2L, storeId));
            assertEquals(exception.getErrorCode(), ErrorCode.NOT_STORE_OWNER);
        }
    }
}
