package org.example.enterprisemultitenantsaasposapplication.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtProvider {

    private final SecretKey key;
    private final String issuer;
    private final long expirationMillis;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.expiration-ms:86400000}") long expirationMillis // default 24h
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.issuer = issuer;
        this.expirationMillis = expirationMillis;
    }

    /**
     * Generate JWT from an Authentication that ALREADY contains authorities.
     */
    public String generateToken(Authentication authentication) {
        String email = authentication.getName();

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a != null && a.startsWith("ROLE_"))
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .issuer(issuer)
                .subject(email)                 // ✅ email in sub
                .issuedAt(now)
                .expiration(expiry)
                .claim("authorities", authorities)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public String getEmail(String token) {
        return parseSignedClaims(token).getPayload().getSubject();
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        Claims claims = parseSignedClaims(token).getPayload();

        String raw = claims.get("authorities", String.class);
        if (raw == null || raw.isBlank()) return Collections.emptyList();

        return Arrays.stream(raw.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Authentication getAuthentication(String token) {
        String email = getEmail(token);
        List<GrantedAuthority> authorities = getAuthorities(token);
        return new UsernamePasswordAuthenticationToken(email, null, authorities);
    }

    public Claims getClaims(String token) {
        return parseSignedClaims(token).getPayload();
    }

    private Jws<Claims> parseSignedClaims(String token) {
        Jws<Claims> jws = Jwts.parser()
                .verifyWith(key)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token);

        String sub = jws.getPayload().getSubject();
        if (sub == null || sub.isBlank()) {
            throw new JwtException("Missing subject (email) in token");
        }

        return jws;
    }
}
