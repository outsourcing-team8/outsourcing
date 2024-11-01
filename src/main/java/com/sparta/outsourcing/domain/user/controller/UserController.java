package com.sparta.outsourcing.domain.user.controller;

import com.sparta.outsourcing.domain.user.dto.request.UserCreateReqDto;
import com.sparta.outsourcing.domain.user.dto.response.UserCreateRespDto;
import com.sparta.outsourcing.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/sign")
    public ResponseEntity<UserCreateRespDto> join(@RequestBody @Valid UserCreateReqDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(dto));
    }

}
