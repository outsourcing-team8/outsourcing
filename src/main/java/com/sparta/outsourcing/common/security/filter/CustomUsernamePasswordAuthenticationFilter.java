package com.sparta.outsourcing.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing.common.security.JwtProvider;
import com.sparta.outsourcing.common.security.LoginUser;
import com.sparta.outsourcing.common.security.dto.LoginReqDto;
import com.sparta.outsourcing.common.security.dto.LoginRespDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        super(authenticationManager);
        setFilterProcessesUrl("/auth/sign");
        this.setAuthenticationManager(authenticationManager);
        this.jwtProvider = jwtProvider;

        log.info("authentication Manager bean = {}", authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            LoginReqDto loginUser = mapper.readValue(request.getInputStream(), LoginReqDto.class);

            UsernamePasswordAuthenticationToken unauthenticated
                    = UsernamePasswordAuthenticationToken.unauthenticated(loginUser.getEmail(), loginUser.getPassword());
            return this.getAuthenticationManager().authenticate(unauthenticated);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        SecurityContextHolder.getContext().setAuthentication(authResult);
        String token = jwtProvider.generateToken((LoginUser) authResult.getPrincipal());

        response.addHeader("Authorization", token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);

        response.getWriter().write(mapper.writeValueAsString(new LoginRespDto((LoginUser) authResult.getPrincipal())));

        log.info("JWT Token 발급 성공: {}", token);
    }
}
