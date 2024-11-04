package com.sparta.outsourcing.domain.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDeleteRespDto {
    private Long userId;

    public UserDeleteRespDto(Long userId) {
        this.userId = userId;
    }
}