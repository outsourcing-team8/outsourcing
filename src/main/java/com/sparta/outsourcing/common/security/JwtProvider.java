package com.sparta.outsourcing.common.security;

import com.sparta.outsourcing.common.exception.CustomApiException;
import com.sparta.outsourcing.common.exception.ErrorCode;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.entity.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;
    private static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_KEY = "auth";

    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] decode = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(decode);
    }

    public String generateToken(LoginUser loginUser) {
        Date now = new Date();
        return TOKEN_PREFIX + Jwts.builder()
                .setSubject(loginUser.getUsername())
                .claim("id", loginUser.getUser().getUserId())
                .claim(AUTHORIZATION_KEY, loginUser.getAuthorities().iterator().next())
                .setExpiration(new Date(now.getTime() + EXPIRATION_TIME))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public LoginUser validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Object id =  claims.getBody().get("id");
            String email = claims.getBody().getSubject();
            String role = (String) ((Map<String, Object>) claims.getBody().get("auth")).get("authority");
            log.info("role = {}" , role);

            return new LoginUser(User.builder().userId(((Number) id).longValue()).email(email).role(UserRole.fromAuthority(role)).build());
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명입니다. ");
            throw new CustomApiException(ErrorCode.INVALID_TOKEN_ERROR);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
            throw new CustomApiException(ErrorCode.INVALID_TOKEN_ERROR);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰입니다.");
            throw new CustomApiException(ErrorCode.INVALID_TOKEN_ERROR);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰입니다. ");
            throw new CustomApiException(ErrorCode.INVALID_TOKEN_ERROR);
        }
    }
}
