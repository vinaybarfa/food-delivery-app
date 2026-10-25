package com.vinay.auth_service.jwt;

import com.vinay.auth_service.entity.User;

import com.vinay.auth_service.repository.UserRepository;

import com.vinay.auth_service.service.MyUserDetailService;

import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JWTAuthenticationFilter
        extends OncePerRequestFilter {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final MyUserDetailService myUserDetailService;

    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request
    ) {

        String path =
                request.getServletPath();

        /*
         * PUBLIC ROUTES
         */

        return path.startsWith("/auth/") || path.startsWith("/api/restaurants");
    }

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    ) throws ServletException, IOException {

        try {

            log.info(
                    "Incoming request : {}",
                    request.getRequestURI()
            );

            /*
             * GET AUTH HEADER
             */
            final String authHeader =
                    request.getHeader("Authorization");

            /*
             * NO TOKEN
             */
            if (

                    authHeader == null ||

                            !authHeader.startsWith("Bearer ")
            ) {

                filterChain.doFilter(
                        request,
                        response
                );

                return;
            }

            /*
             * EXTRACT JWT
             */
            String jwtToken =
                    authHeader.substring(7);

            /*
             * EXTRACT EMAIL
             */
            String email =
                    jwtService.extractEmail(jwtToken);

            /*
             * VALIDATE AUTHENTICATION
             */
            if (

                    email != null &&

                            SecurityContextHolder
                                    .getContext()
                                    .getAuthentication() == null
            ) {

                User user =
                        userRepository
                                .findByEmail(email)
                                .orElse(null);

                /*
                 * VALID TOKEN
                 */
                if (

                        user != null &&

                                jwtService.isTokenValid(jwtToken)
                ) {

                    UserDetails userDetails =
                            myUserDetailService
                                    .loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authenticationToken =

                            new UsernamePasswordAuthenticationToken(

                                    userDetails,

                                    null,

                                    userDetails.getAuthorities()
                            );

                    authenticationToken.setDetails(

                            new WebAuthenticationDetailsSource()

                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(
                    request,
                    response
            );

        } catch (Exception e) {

            log.error(
                    "JWT authentication failed : {}",
                    e.getMessage()
            );

            filterChain.doFilter(
                    request,
                    response
            );
        }
    }
}