package com.sparta.outsourcing.domain.user.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.user.dto.request.UserCreateReqDto;
import com.sparta.outsourcing.domain.user.dto.response.UserCreateRespDto;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCreateRespDto register(UserCreateReqDto dto) {
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {throw new CustomApiException(ErrorCode.ALREADY_USER_EXIST);});

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return new UserCreateRespDto(userRepository.save(dto.toEntity()).getUserId());
    }

}
