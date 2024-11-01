package com.sparta.outsourcing.domain.user.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRespDto {

    private Long userId;

    public UserCreateRespDto(Long userId) {
        this.userId = userId;
    }
}
