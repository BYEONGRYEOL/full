package com.example.full.config.security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//    @Bean
//    @Order(0)
//    public SecurityFilterChain resources(HttpSecurity http) throws Exception{
//        http.requestMatchers
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // default가 csrf와 cors가 모두 disabled 된 상황
        http.authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .anyRequest().authenticated()// 어떤 요청이 와도 인증
                )
                .formLogin(login -> login // form 방식 로그인 사용 선언
                        .defaultSuccessUrl("/성공시 url", true)
                        .permitAll()
                )
                .logout(Customizer.withDefaults()); // 기본은 /logout으로 인증해제

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
