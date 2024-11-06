package com.sparta.outsourcing.config;

import com.sparta.outsourcing.common.security.JwtProvider;
import com.sparta.outsourcing.common.security.filter.CustomAuthorizationFilter;
import com.sparta.outsourcing.common.security.filter.CustomUsernamePasswordAuthenticationFilter;
import com.sparta.outsourcing.common.security.filter.GlobalFilterExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    @Profile("dev")
    public SecurityFilterChain securityFilterChainDev(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);
        log.info("Security Filter Chain Dev 버전 빈 등록");
        return httpSecurity.build();
    }


    @Bean
    @Profile("test")
    public SecurityFilterChain securityFilterChainTest(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/join").permitAll()
                        .requestMatchers("/auth/sign").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/stores").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST,"/api/stores/{storeId}/menus").hasRole("OWNER")
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .addFilterBefore(new GlobalFilterExceptionHandler(),
                        CustomAuthorizationFilter.class)
                .addFilterBefore(new CustomAuthorizationFilter(authenticationManager(authenticationConfiguration), jwtProvider),
                        CustomUsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomUsernamePasswordAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtProvider),
                        UsernamePasswordAuthenticationFilter.class)
        ;

        log.info("Security Filter Chain Test 버전 빈 등록");
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
