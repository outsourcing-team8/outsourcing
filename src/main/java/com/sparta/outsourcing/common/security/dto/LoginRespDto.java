package com.sparta.outsourcing.common.security.dto;

import com.sparta.outsourcing.common.security.LoginUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRespDto {

    private Long userId;

    public LoginRespDto(LoginUser loginUser) {
        this.userId = loginUser.getUser().getUserId();
    }
}
