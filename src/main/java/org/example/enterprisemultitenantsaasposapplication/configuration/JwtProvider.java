package org.example.enterprisemultitenantsaasposapplication.configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtProvider {

    private final SecretKey key;
    private final String issuer;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.issuer}") String issuer
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.issuer = issuer;
    }

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date expiry = new Date(now.getTime() + 24L * 60 * 60 * 1000);

        return Jwts.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiration(expiry)
                .claim("email", email)
                .claim("authorities", authorities)
                .signWith(key)
                .compact();
    }
}
