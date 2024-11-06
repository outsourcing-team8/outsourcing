package com.sparta.outsourcing.common.oauth2;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class NaverOAuthUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;


    @Override
    public String getId() {
        return (String) ((Map<String, Object>) attributes.get("response")).get("id");
    }

    @Override
    public String getEmail() {
        return (String) ((Map<String, Object>) attributes.get("response")).get("email");
    }
}
