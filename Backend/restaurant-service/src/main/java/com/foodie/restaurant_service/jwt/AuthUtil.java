package com.foodie.restaurant_service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;

    /*
     * SIGNING KEY
     */
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                secretKey.getBytes(
                        StandardCharsets.UTF_8
                )
        );
    }

    /*
     * EXTRACT CLAIMS
     */
    private Claims extractClaims(
            String token
    ) {

        return Jwts.parser()

                .verifyWith(getSigningKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }

    /*
     * EXTRACT EMAIL
     */
    public String extractEmail(
            String token
    ) {

        return extractClaims(token)
                .getSubject();
    }

    /*
     * EXTRACT ROLE
     */
    public String extractRole(
            String token
    ) {

        Claims claims =
                extractClaims(token);

        List<String> roles =
                claims.get(
                        "roles",
                        List.class
                );

        if (

                roles == null ||

                        roles.isEmpty()

        ) {

            return null;
        }

        String role =
                roles.get(0);

        /*
         * REMOVE ROLE_ PREFIX
         */
        if (

                role.startsWith("ROLE_")

        ) {

            role =
                    role.substring(5);
        }

        return role;
    }

    /*
     * VALID TOKEN
     */
    public boolean isTokenValid(
            String token
    ) {

        try {

            extractClaims(token);

            return true;

        } catch (Exception e) {

            log.error(
                    "Invalid JWT : {}",
                    e.getMessage()
            );

            return false;
        }
    }
}