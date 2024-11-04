package com.sparta.outsourcing.domain.user.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.user.dto.request.UserCreateReqDto;
import com.sparta.outsourcing.domain.user.dto.request.UserDeleteReqDto;
import com.sparta.outsourcing.domain.user.dto.response.UserCreateRespDto;
import com.sparta.outsourcing.domain.user.dto.response.UserDeleteRespDto;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCreateRespDto register(UserCreateReqDto dto) {
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new CustomApiException(ErrorCode.ALREADY_USER_EXIST);
                });
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return new UserCreateRespDto(userRepository.save(dto.toEntity()).getUserId());
    }

    public UserDeleteRespDto deleteUser(Long userId, UserDeleteReqDto dto) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            throw new CustomApiException(ErrorCode.MiSS_MATCH_PASSWORD);
        }

        userRepository.delete(findUser);
        SecurityContextHolder.clearContext();

        return new UserDeleteRespDto(findUser.getUserId());
    }

}
