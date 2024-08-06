package com.study.module.auth.application.service;

import com.study.module.auth.adapter.output.JwtPersistenceAdapter;
import com.study.module.auth.application.port.input.JwtUseCase;
import com.study.module.auth.domain.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtService implements JwtUseCase {
    @Value("${spring.jwt.secret}")
    private String secret;

    @Value("${spring.jwt.refresh.expire}")
    private Long refreshExpire;

    @Value("${spring.jwt.access.expire}")
    private Long accessExpire;

    private Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    private final JwtPersistenceAdapter jwtPersistenceAdapter;

    @Override
    public String createAccessToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpire))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String createRefreshToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpire))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String reCreateAccessToken(String refreshToken) throws Exception {
        TokenInfo tokenInfo = findByRefreshToken(refreshToken);

        if(tokenInfo == null || Objects.equals(tokenInfo.refreshToken(), refreshToken)) {
            throw new Exception();
        }

        Jws<Claims> claims = validateToken(refreshToken);
        if(claims != null) {
            return createAccessToken(claims.getBody().getSubject());
        } else {
            throw new Exception();
        }
    }

    public Jws<Claims> validateToken(String token) throws Exception {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public TokenInfo findByRefreshToken(String refreshToken) {
        return jwtPersistenceAdapter.findByRefreshToken(refreshToken);
    }
}
