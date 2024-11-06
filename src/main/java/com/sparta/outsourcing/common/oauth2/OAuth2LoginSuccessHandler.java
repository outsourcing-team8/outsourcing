package com.sparta.outsourcing.common.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing.common.security.JwtProvider;
import com.sparta.outsourcing.common.security.LoginUser;
import com.sparta.outsourcing.domain.user.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        LoginUser loginUser = new LoginUser(User.builder().userId(oAuth2User.getId()).email(oAuth2User.getName()).role(oAuth2User.getRole()).build());
        String token = jwtProvider.generateToken(loginUser);

        log.info("Token 정상 발급 : {}", token);

        OAuthLoginRespDto dto = new OAuthLoginRespDto(loginUser.getUser().getUserId(), loginUser.getUsername(), token);

        response.setHeader("Authorization","Bearer " + token);
        response.setStatus(200);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(dto));

    }
}
