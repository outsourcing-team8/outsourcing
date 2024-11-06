package com.sparta.outsourcing.domain.menu.service;

import com.sparta.outsourcing.common.security.LoginUser;
import com.sparta.outsourcing.domain.menu.dto.request.MenuCreateReqDto;
import com.sparta.outsourcing.domain.menu.dto.response.MenuCreateRespDto;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;
import com.sparta.outsourcing.domain.user.entity.Address;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    private static final Logger log = LoggerFactory.getLogger(MenuServiceTest.class);
    @Mock
    private MenuRepository menuRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private MenuService menuService;
    User user;
    Store store;
    LoginUser loginUser;
    @BeforeEach
    void setUp() {
        Long userId = 1L;
        Address address = new Address();
        address.setStreet("APT");
        address.setDetails("100/100");
        address.setPostCode("111-111");

        user = User.builder()
                .userId(userId)
                .email("asdf@naver.com")
                .password("password1!")
                .nickname("user1")
                .role(UserRole.OWNER)
                .phoneNumber("010-0000-0000")
                .address(address)
                .build();

        loginUser = new LoginUser(user);

        store = new Store(
                user, "양념 치킨", LocalTime.of(9, 30), LocalTime.of(20, 0), 100);
    }

    @Test
    @DisplayName("메뉴 생성 API 테스트")
    void createMenu() {
        // Given

        Menu menu = new Menu(store, "프라이드 치킨", 23000, false);

        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(menuRepository.findByStoreStoreIdAndName(1L, "프라이드 치킨")).thenReturn(null);

        MenuCreateReqDto reqDto = MenuCreateReqDto.builder()
                .name("프라이드 치킨")
                .price(23000)
                .build();

        when(menuRepository.save(any(Menu.class))).thenReturn(menu);
        // When
        MenuCreateRespDto resp = menuService.createMenu(1L, reqDto, loginUser);

        // Then
        assertThat(resp).isNotNull();
        assertThat(resp.getId()).isEqualTo(menu.getMenuId());


    }


}