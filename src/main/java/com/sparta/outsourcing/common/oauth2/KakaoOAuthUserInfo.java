package com.sparta.outsourcing.common.oauth2;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoOAuthUserInfo implements OAuth2UserInfo{

    private final Map<String, Object> attributes;

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getEmail() {
        Map<String, Object> accountKey = (Map<String, Object>) attributes.get("kakao_account");
        return (String) accountKey.get("email");
    }
}
