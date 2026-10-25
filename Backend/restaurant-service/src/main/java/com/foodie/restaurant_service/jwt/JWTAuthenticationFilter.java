package com.foodie.restaurant_service.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.User;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter
        extends OncePerRequestFilter {

    private final AuthUtil authUtil;

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    ) throws ServletException, IOException {

        try {

            final String authHeader =
                    request.getHeader(
                            "Authorization"
                    );

            /*
             * NO TOKEN
             */
            if (

                    authHeader == null ||

                            !authHeader.startsWith(
                                    "Bearer "
                            )

            ) {

                filterChain.doFilter(
                        request,
                        response
                );

                return;
            }

            /*
             * EXTRACT TOKEN
             */
            String jwtToken =
                    authHeader.substring(7);

            /*
             * INVALID TOKEN
             */
            if (

                    !authUtil.isTokenValid(
                            jwtToken
                    )

            ) {

                filterChain.doFilter(
                        request,
                        response
                );

                return;
            }

            /*
             * EXTRACT EMAIL
             */
            String email =
                    authUtil.extractEmail(
                            jwtToken
                    );

            /*
             * EXTRACT ROLE
             */
            String role =
                    authUtil.extractRole(
                            jwtToken
                    );

            log.info(
                    "Authenticated User : {}",
                    email
            );

            log.info(
                    "Role : {}",
                    role
            );

            /*
             * NULL ROLE
             */
            if (

                    role == null ||

                            role.isBlank()

            ) {

                filterChain.doFilter(
                        request,
                        response
                );

                return;
            }

            /*
             * AUTHORITIES
             */
            List<SimpleGrantedAuthority>
                    authorities =

                    List.of(

                            new SimpleGrantedAuthority(
                                    "ROLE_" + role
                            )
                    );

            /*
             * CREATE USER
             */
            User user =
                    new User(

                            email,

                            "",

                            authorities
                    );

            /*
             * AUTH TOKEN
             */
            UsernamePasswordAuthenticationToken authentication =

                    new UsernamePasswordAuthenticationToken(

                            user,

                            null,

                            authorities
                    );

            authentication.setDetails(

                    new WebAuthenticationDetailsSource()

                            .buildDetails(request)
            );

            /*
             * SET CONTEXT
             */
            SecurityContextHolder

                    .getContext()

                    .setAuthentication(
                            authentication
                    );

        } catch (Exception e) {

            log.error(
                    "JWT Authentication Failed : {}",
                    e.getMessage()
            );
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}