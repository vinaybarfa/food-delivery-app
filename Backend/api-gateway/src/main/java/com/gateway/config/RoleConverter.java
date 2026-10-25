package com.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RoleConverter
        implements Converter<Jwt,
        Collection<GrantedAuthority>> {

    @Override
    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority>
    convert(Jwt jwt) {

        try {

            Map<String, Object> realmAccess =
                    jwt.getClaim("realm_access");

            if (realmAccess == null) {

                log.warn(
                        "No realm_access found in JWT"
                );

                return Collections.emptyList();
            }

            List<String> roles =
                    (List<String>)
                            realmAccess.get("roles");

            if (roles == null || roles.isEmpty()) {

                log.warn(
                        "No roles found in JWT"
                );

                return Collections.emptyList();
            }

            return roles.stream()

                    .map(role ->
                            new SimpleGrantedAuthority(
                                    "ROLE_" + role.toUpperCase()
                            )
                    )

                    .collect(Collectors.toSet());

        } catch (Exception ex) {

            log.error(
                    "Failed to extract roles from JWT: {}",
                    ex.getMessage()
            );

            return Collections.emptyList();
        }
    }
}