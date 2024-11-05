package com.sparta.outsourcing.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.common.security.JwtProvider;
import com.sparta.outsourcing.common.security.LoginUser;
import com.sparta.outsourcing.domain.user.dto.request.UserCreateReqDto;
import com.sparta.outsourcing.domain.user.dto.request.UserDeleteReqDto;
import com.sparta.outsourcing.domain.user.dto.response.UserCreateRespDto;
import com.sparta.outsourcing.domain.user.dto.response.UserDeleteRespDto;
import com.sparta.outsourcing.domain.user.entity.Address;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.entity.UserRole;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import com.sparta.outsourcing.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("정삭적으로 회원가입 성공")
    void joinTest() throws Exception {
        // given
        Address address = new Address();
        address.setStreet("APT");
        address.setDetails("100/100");
        address.setPostCode("100-100");
        UserCreateReqDto reqDto = new UserCreateReqDto("test@example.com", "tset", "password1!", UserRole.USER, "010-0000-0000", address);
        UserCreateRespDto respDto = new UserCreateRespDto(1L);
        when(userService.register(any(UserCreateReqDto.class))).thenReturn(respDto);

        // when & then
        mockMvc.perform(post("/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reqDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    @DisplayName("회원가입 시 잘못된 이메일 형식")
    public void joinFailureInvalidEmailTest() throws Exception {
        // given
        Address address = new Address();
        address.setStreet("APT");
        address.setDetails("100/100");
        address.setPostCode("100-100");
        UserCreateReqDto reqDto = new UserCreateReqDto("XXXXXX", "tset", "password1!", UserRole.USER, "010-0000-0000", address);

        // when & then
        mockMvc.perform(post("/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reqDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 시 잘못된 비밀번호 형식")
    public void joinFailureInvalidPasswordTest() throws Exception {
        // given
        Address address = new Address();
        address.setStreet("APT");
        address.setDetails("100/100");
        address.setPostCode("100-100");
        UserCreateReqDto reqDto = new UserCreateReqDto("test@example.com", "tset", "test1234", UserRole.USER, "010-0000-0000", address);

        // when & then
        mockMvc.perform(post("/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reqDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 시 주소 입력을 안한 경우")
    public void joinFailureNotFoundAddressTest() throws Exception {
        // given
        UserCreateReqDto reqDto = new UserCreateReqDto("test@example.com", "tset", "test1234", UserRole.USER, "010-0000-0000", null);

        // when & then
        mockMvc.perform(post("/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reqDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 가입시 동일한 이메일이 존재하는 경우")
    public void joinFailureExistEmailTest() throws Exception {
        // given
        Address address = new Address();
        address.setStreet("APT");
        address.setDetails("100/100");
        address.setPostCode("100-100");
        UserCreateReqDto reqDto = new UserCreateReqDto("test@example.com", "tset", "password1!", UserRole.USER, "010-0000-0000", address);
        when(userService.register(any(UserCreateReqDto.class))).thenThrow(new CustomApiException(ErrorCode.ALREADY_USER_EXIST));

        // when & then
        mockMvc.perform(post("/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reqDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("정상적으로 회월 탈퇴 성공")
    public void deleteUserSuccessTest() throws Exception {
        // given
        UserDeleteReqDto reqDto = new UserDeleteReqDto("password1!");
        UserDeleteRespDto respDto = new UserDeleteRespDto(1L);
        when(userService.deleteUser(any(), any(UserDeleteReqDto.class))).thenReturn(respDto);

        // when & then
        mockMvc.perform(delete("/auth/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reqDto))
                        .header("Authorization", "Bearer " + generateJwtToken())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    @DisplayName("회원탈퇴 시 비밀번호가 틀린 경우")
    public void deleteUserMissMatchPasswordTest() throws Exception {
        // given
        UserDeleteReqDto reqDto = new UserDeleteReqDto("password1!");
        User user = User.builder().userId(1L).email("test@example.com").password("test1234").build();

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userService.deleteUser(any(), any(UserDeleteReqDto.class)))
                .thenThrow(new CustomApiException(ErrorCode.MISS_MATCH_PASSWORD));

        // when & then
        mockMvc.perform(delete("/auth/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reqDto))
                        .header("Authorization", "Bearer " + generateJwtToken())
                )
                .andExpect(status().isBadRequest());
    }

    private String generateJwtToken() {
        User user = User.builder().userId(1L).email("test@example.com").role(UserRole.USER).build();
        return jwtProvider.generateToken(new LoginUser(user));
    }
}