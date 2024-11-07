package com.sparta.outsourcing.common.oauth2;

import lombok.Getter;

@Getter
public class OAuthLoginRespDto {
    private Long id;
    private String email;
    private String token;

    public OAuthLoginRespDto(Long id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }
}
