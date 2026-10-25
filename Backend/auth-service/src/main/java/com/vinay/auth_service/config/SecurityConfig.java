package com.vinay.auth_service.config;

import com.vinay.auth_service.jwt.JWTAuthenticationFilter;
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

    /*
     * JWT FILTER
     */
    private final JWTAuthenticationFilter filter;
    private final SecurityExceptionHandler
            securityExceptionHandler;
    /*
     * SECURITY FILTER CHAIN
     */
    @Bean
    public SecurityFilterChain securityWebFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                /*
                 * DISABLE CSRF
                 */
                .csrf(csrf -> csrf.disable())

                /*
                 * ENABLE CORS
                 */
                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()
                        )
                )

                /*
                 * STATELESS SESSION
                 */
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                ).exceptionHandling(ex -> ex

                        .authenticationEntryPoint(
                                securityExceptionHandler
                        )

                        .accessDeniedHandler(
                                securityExceptionHandler
                        )
                )

                /*
                 * AUTHORIZATION RULES
                 */
                .authorizeHttpRequests(auth -> auth

                        /*
                         * PUBLIC ROUTES
                         */
                        .requestMatchers(

                                "/auth/**",

                                "/error",

                                "/v3/api-docs/**",

                                "/swagger-ui/**",

                                "/swagger-ui.html"

                        ).permitAll()

                        /*
                         * ADMIN ROUTES
                         */
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "RESTAURANT_OWNER"
                        )

                        /*
                         * CUSTOMER ROUTES
                         */
                        .requestMatchers(
                                "/api/restaurant/**", "/api/user/**"
                        ).authenticated()

                        /*
                         * ALL OTHER ROUTES
                         */
                        .anyRequest()
                        .authenticated()
                )

                /*
                 * JWT FILTER
                 */
                .addFilterBefore(
                        filter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    /*
     * CORS CONFIGURATION
     */
    @Bean
    public CorsConfigurationSource
    corsConfigurationSource() {

        return new CorsConfigurationSource() {

            @Override
            public CorsConfiguration getCorsConfiguration(
                    HttpServletRequest request
            ) {

                CorsConfiguration cfg =
                        new CorsConfiguration();

                /*
                 * ALLOWED ORIGINS
                 */
                cfg.setAllowedOrigins(

                        Arrays.asList(

                                "http://localhost:5173",

                                "http://localhost:3000",

                                "https://zosh-food.versal.app"
                        )
                );

                /*
                 * ALLOWED METHODS
                 */
                cfg.setAllowedMethods(

                        Arrays.asList(

                                "GET",

                                "POST",

                                "PUT",

                                "DELETE",

                                "PATCH",

                                "OPTIONS"
                        )
                );

                /*
                 * ALLOWED HEADERS
                 */
                cfg.setAllowedHeaders(
                        Collections.singletonList("*")
                );

                /*
                 * EXPOSED HEADERS
                 */
                cfg.setExposedHeaders(

                        Arrays.asList(
                                "Authorization"
                        )
                );

                /*
                 * ALLOW CREDENTIALS
                 */
                cfg.setAllowCredentials(true);

                /*
                 * PREFLIGHT CACHE
                 */
                cfg.setMaxAge(3600L);

                return cfg;
            }
        };
    }

    /*
     * PASSWORD ENCODER
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /*
     * AUTHENTICATION MANAGER
     */
    @Bean
    public AuthenticationManager authenticationManager(

            AuthenticationConfiguration configuration

    ) throws Exception {

        return configuration
                .getAuthenticationManager();
    }
}