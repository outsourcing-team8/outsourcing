package com.sparta.outsourcing.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDeleteReqDto {

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;
}
