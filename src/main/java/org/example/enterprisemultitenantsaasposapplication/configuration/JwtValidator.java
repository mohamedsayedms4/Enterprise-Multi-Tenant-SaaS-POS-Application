package org.example.enterprisemultitenantsaasposapplication.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtValidator extends OncePerRequestFilter {

    private final SecretKey key;
    private final String issuer;

    public JwtValidator(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.issuer}") String issuer
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.issuer = issuer;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader(JwtConstant.JWT_HEADER);

        if (header == null || !header.startsWith(JwtConstant.BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = header.substring(JwtConstant.BEARER_PREFIX.length());

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .requireIssuer(issuer)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();

            String email = claims.getSubject();
            String authorities = claims.get("authorities", String.class);

            var auth = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(authorities == null ? "" : authorities)
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            throw new BadCredentialsException("Invalid JWT token", e);
        }

        filterChain.doFilter(request, response);
    }
}
