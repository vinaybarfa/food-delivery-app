package com.vinay.auth_service.jwt;

import com.vinay.auth_service.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    private SecretKey key;

    /*
     * INITIALIZE SECRET KEY
     */
    @PostConstruct
    public void init() {

        this.key =
                Keys.hmacShaKeyFor(
                        secretKey.getBytes(
                                StandardCharsets.UTF_8
                        )
                );
    }

    /*
     * GENERATE ACCESS TOKEN
     */
    public String generateAccessToken(
            User user
    ) {

        return Jwts.builder()

                .subject(user.getEmail())

                .claim(
                        "userId",
                        user.getId()
                )

                .claim(
                        "roles",
                        List.of(
                                "ROLE_" + user.getRole().name()
                        )
                )

                .claim(
                        "tokenType",
                        "ACCESS"
                )

                .issuer("food-delivery-auth-service")

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + accessTokenExpiration
                        )
                )

                .signWith(key)

                .compact();
    }

    /*
     * GENERATE REFRESH TOKEN
     */
    public String generateRefreshToken(
            User user
    ) {

        return Jwts.builder()

                .subject(user.getEmail())

                .claim(
                        "tokenType",
                        "REFRESH"
                )

                .issuer("food-delivery-auth-service")

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + refreshTokenExpiration
                        )
                )

                .signWith(key)

                .compact();
    }

    /*
     * VALIDATE TOKEN
     */
    public boolean isTokenValid(
            String token
    ) {

        try {

            return !isTokenExpired(token);

        } catch (Exception ex) {

            log.error(
                    "Invalid JWT token: {}",
                    ex.getMessage()
            );

            return false;
        }
    }

    /*
     * EXTRACT EMAIL
     */
    public String extractEmail(
            String token
    ) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }

    /*
     * EXTRACT ROLE
     */
    public Object extractRoles(
            String token
    ) {

        return extractClaim(
                token,
                claims -> claims.get("roles")
        );
    }

    /*
     * EXTRACT TOKEN TYPE
     */
    public String extractTokenType(
            String token
    ) {

        return extractClaim(
                token,
                claims -> claims.get(
                        "tokenType",
                        String.class
                )
        );
    }

    /*
     * EXTRACT EXPIRATION
     */
    public Date extractExpiration(
            String token
    ) {

        return extractClaim(
                token,
                Claims::getExpiration
        );
    }

    /*
     * EXTRACT CLAIM
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {

        Claims claims =
                extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    /*
     * CHECK TOKEN EXPIRATION
     */
    private boolean isTokenExpired(
            String token
    ) {

        return extractExpiration(token)
                .before(new Date());
    }

    /*
     * PARSE JWT
     */
    private Claims extractAllClaims(
            String token
    ) {

        return Jwts.parser()

                .verifyWith(key)

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }
}