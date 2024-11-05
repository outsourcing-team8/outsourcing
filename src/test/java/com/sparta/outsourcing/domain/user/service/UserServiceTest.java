package com.sparta.outsourcing.domain.user.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.domain.user.dto.request.UserCreateReqDto;
import com.sparta.outsourcing.domain.user.dto.request.UserDeleteReqDto;
import com.sparta.outsourcing.domain.user.dto.response.UserCreateRespDto;
import com.sparta.outsourcing.domain.user.dto.response.UserDeleteRespDto;
import com.sparta.outsourcing.domain.user.entity.Address;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.entity.UserRole;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("정상적으로 회원가입이 동작")
    public void successRegisterTest() {
        // given
        Address address = new Address();
        address.setStreet("APT");
        address.setDetails("100/100");
        address.setPostCode("100-100");
        UserCreateReqDto dto = new UserCreateReqDto("test@example.com", "tset", "password1!", UserRole.USER, "010-0000-0000", address);
        User savedUser = User.builder().userId(1L).email("test@example").password("encodedPassword").build();
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(savedUser);
        // when
        UserCreateRespDto register = userService.register(dto);

        // then
        assertThat(register).isNotNull();
        assertThat(register.getUserId()).isEqualTo(1L);
        verify(userRepository, times(1)).findByEmail(dto.getEmail());
        verify(passwordEncoder, times(1)).encode(any());
    }

    @Test
    @DisplayName("이미 존재하는 사용자 테스트")
    public void existEmailTest() {
        // given
        Address address = new Address();
        address.setStreet("APT");
        address.setDetails("100/100");
        address.setPostCode("100-100");
        UserCreateReqDto dto = new UserCreateReqDto("test@example.com", "tset", "password1!", UserRole.USER, "010-0000-0000", address);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(User.builder().email("test@example").build()));

        // when & then
        assertThrows(CustomApiException.class, () -> userService.register(dto));
    }

    @Test
    @DisplayName("성공적으로 유저 삭제")
    public void deleteSuccessTest() {
        // given
        Long userId = 1L;
        UserDeleteReqDto dto = new UserDeleteReqDto("password1!");
        User findUser = User.builder().userId(1L).email("test@example").password("encodedPassword").build();
        when(userRepository.findById(any())).thenReturn(Optional.of(findUser));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        doNothing().when(userRepository).delete(findUser);

        // when
        UserDeleteRespDto respDto = userService.deleteUser(userId, dto);

        // then
        assertThat(respDto).isNotNull();
        verify(userRepository, times(1)).findById(any());
        verify(passwordEncoder, times(1)).matches(any(), any());
        verify(userRepository, times(1)).delete(findUser);

    }

    @Test
    @DisplayName("유저 삭제 시 유저를 찾을 수 없을 경우")
    public void notFoundDeleteUserTest() {
        // given
        Long userId = 1L;
        UserDeleteReqDto dto = new UserDeleteReqDto("password1!");

        // when & then
        assertThrows(CustomApiException.class, () -> userService.deleteUser(userId, dto));
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("유저 삭제 시 패스워드 틀린 경우")
    public void passwordMissMatchDeleteUserTest() {
        // given
        Long userId = 1L;
        UserDeleteReqDto dto = new UserDeleteReqDto("password1!");
        User findUser = User.builder().userId(1L).email("test@example").password("encodedPassword").build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(findUser));
        when(passwordEncoder.matches(dto.getPassword(), findUser.getPassword())).thenReturn(false);

        // when & then
        assertThrows(CustomApiException.class, () -> userService.deleteUser(userId, dto));
        verify(userRepository, times(1)).findById(any());
        verify(passwordEncoder, times(1)).matches(any(), any());
    }
}