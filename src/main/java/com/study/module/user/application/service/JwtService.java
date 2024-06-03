package com.study.module.user.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.study.module.user.application.port.input.JwtRegisterUseCase;
import com.study.module.user.application.port.input.JwtRemoveUseCase;
import com.study.module.user.application.port.output.JwtModifyPort;
import com.study.module.user.application.port.output.JwtRegisterPort;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtService implements JwtRegisterUseCase, JwtRemoveUseCase {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.access.expire}")
    private Long accessTokenExpire;

    @Value("${spring.jwt.access.header}")
    private String accessTokenHeader;

    @Value("${spring.jwt.refresh.expire}")
    private Long refreshTokenExpire;

    @Value("${spring.jwt.refresh.header}")
    private String refreshTokenHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String USERNAME_CLAIM = "userId";
    private static final String BEARER = "Bearer";
    private final JwtRegisterPort jwtRegisterPort;
    private final JwtModifyPort jwtModifyPort;
//    private final UserModifyPort userModifyPort;

    @Override
    public String createAccessToken(String userId) {
        Date date = Date.from(LocalDateTime.now().plus(accessTokenExpire, ChronoUnit.SECONDS).atZone(ZoneId.systemDefault()).toInstant());
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(date)
                .withClaim(USERNAME_CLAIM, userId)
                .sign(Algorithm.HMAC512(secretKey));
    }

    @Override
    public String createRefreshToken() {
        Date date = Date.from(LocalDateTime.now().plus(refreshTokenExpire, ChronoUnit.SECONDS).atZone(ZoneId.systemDefault()).toInstant());
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(date)
                .sign(Algorithm.HMAC512(secretKey));
    }

//    @Override
//    public void destroyRefreshToken(String userId) {
//        userModifyPort.findById(userId)
//    }
//
//    @Override
//    public void updateRefreshToken(String userId, String refreshToken) {
//        userModifyPort.findById(userId)
//    }
//
//    @Override
//    public void sendAccessAndRefreshToken()
}
