package com.study.module.auth.application.service;

import com.study.module.auth.domain.JwtToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
//@RequiredArgsConstructor
public class JwtTokenProvider {
    private final Key key;
    private final Long accessExpire;
    private final Long refreshExpire;
    public JwtTokenProvider(@Value("${spring.jwt.secret}") String secret,
                            @Value("${spring.jwt.refresh.expire}") Long refreshExpire,
                            @Value("${spring.jwt.access.expire}") Long accessExpire) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.refreshExpire = refreshExpire;
        this.accessExpire = accessExpire;

    }

//    @Value("${spring.jwt.secret}")
//    private String secretKey;
//    @Value("${spring.jwt.refresh.expire}")
//    private Long refreshExpire;
//
//    @Value("${spring.jwt.access.expire}")
//    private Long accessExpire;
//    private final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("sdfsdfsdfsfdfsdfsdfsdfsdf"));


    /**
     * accessToken, refreshToken 생성
     */
    public JwtToken generateToken(Authentication authentication) {
        String authority = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .claim("auth", authority)
                .setExpiration(new Date(System.currentTimeMillis() + accessExpire))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpire))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new JwtToken(
                "Bearer", accessToken, refreshToken
        );

    }

    /**
     * jwt 복호화 토큰 정보
     */
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaim(accessToken);

        if(claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserDetails userDetails = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT token" , e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty", e);
        }
        return false;
    }

    private Claims parseClaim(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
