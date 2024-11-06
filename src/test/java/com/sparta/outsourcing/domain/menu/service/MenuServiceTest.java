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

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private StoreRepository storeRepository;
    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    Store store;
    LoginUser loginUser;
    User user;


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

        Long storeId = 1L;
        String name = "치킨집";
        LocalTime openAt = LocalTime.of(14, 0);
        LocalTime closeAt = LocalTime.of(0, 0);
        int minPrice = 10000;
        store = Store.builder()
                .storeId(storeId)
                .owner(user)
                .name(name)
                .openAt(openAt)
                .closedAt(closeAt)
                .minPrice(minPrice)
                .build();
    }

    @Test
    @DisplayName("메뉴 생성 테스트")
    void createMenu() {
        //given
        Menu menu = new Menu(store, "프라이드 치킨", 23000, false);

        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(menuRepository.findByStoreStoreIdAndName(1L, "프라이드 치킨")).thenReturn(null);

        MenuCreateReqDto reqDto = MenuCreateReqDto.builder()
                .name("프라이드 치킨")
                .price(23000)
                .build();

        when(menuRepository.save(any(Menu.class))).thenReturn(menu);
        //when
        MenuCreateRespDto resp = menuService.createMenu(1L, reqDto, loginUser);
        //then
        assertThat(resp).isNotNull();
        assertThat(resp.getId()).isEqualTo(menu.getMenuId());

    }


}