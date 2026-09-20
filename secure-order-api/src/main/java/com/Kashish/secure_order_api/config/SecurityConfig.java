package com.Kashish.secure_order_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Kashish.secure_order_api.security.JwtAuthenticationfilter;
import com.Kashish.secure_order_api.service.JwtService;
import com.Kashish.secure_order_api.service.UserService;

@Configuration
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserService userService;

    public SecurityConfig(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .requestMatchers(
                    "/api/products/**",
                    "/api/customers/**",
                    "/api/orders/**"
                ).authenticated()
                .anyRequest().authenticated()
            ).exceptionHandling(exception -> exception.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .addFilterBefore(
                new JwtAuthenticationfilter(jwtService, userService),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}

