package com.sparta.outsourcing.domain.user.service;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.user.dto.request.UserCreateReqDto;
import com.sparta.outsourcing.domain.user.dto.response.UserCreateRespDto;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserCreateRespDto register(UserCreateReqDto dto) {
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {throw new CustomApiException(ErrorCode.ALREADY_USER_EXIST);});

        return new UserCreateRespDto(userRepository.save(new User(dto)).getUserId());
    }

}
