package com.sparta.outsourcing.domain.user.controller;

import com.sparta.outsourcing.common.security.LoginUser;
import com.sparta.outsourcing.domain.user.dto.request.UserCreateReqDto;
import com.sparta.outsourcing.domain.user.dto.request.UserDeleteReqDto;
import com.sparta.outsourcing.domain.user.dto.response.UserCreateRespDto;
import com.sparta.outsourcing.domain.user.dto.response.UserDeleteRespDto;
import com.sparta.outsourcing.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/join")
    public ResponseEntity<UserCreateRespDto> join(@RequestBody @Valid UserCreateReqDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(dto));
    }

    @DeleteMapping("/auth/users")
    public ResponseEntity<UserDeleteRespDto> deleteCurrentUser(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody @Valid UserDeleteReqDto reqDto
    ) {
        UserDeleteRespDto respDto = userService.deleteUser(loginUser.getUser().getUserId(), reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(respDto);
    }
}
