package com.foodie.restaurant_service.security;

import com.foodie.restaurant_service.jwt.JWTAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(

            HttpSecurity http

    ) throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .cors(cors ->

                        cors.configurationSource(
                                corsConfigurationSource()
                        )
                )

                .sessionManagement(session ->

                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/auth/**"
                        ).permitAll()

                        .requestMatchers(
                                "/api/restaurant/**"
                        ).permitAll()

                        .requestMatchers(
                                "/api/admin/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "RESTAURANT_OWNER"
                        )

                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(

                        filter,

                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    /*
     * CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        return new CorsConfigurationSource() {

            @Override
            public CorsConfiguration getCorsConfiguration(

                    HttpServletRequest request

            ) {

                CorsConfiguration cfg =
                        new CorsConfiguration();

                cfg.setAllowedOrigins(

                        Arrays.asList(

                                "http://localhost:5173",

                                "http://localhost:3000"
                        )
                );

                cfg.setAllowedMethods(
                        Collections.singletonList("*")
                );

                cfg.setAllowedHeaders(
                        Collections.singletonList("*")
                );

                cfg.setExposedHeaders(
                        Arrays.asList("Authorization")
                );

                cfg.setAllowCredentials(true);

                cfg.setMaxAge(3600L);

                return cfg;
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(

            AuthenticationConfiguration configuration

    ) throws Exception {

        return configuration.getAuthenticationManager();
    }
}