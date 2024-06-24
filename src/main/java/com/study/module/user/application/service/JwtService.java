package com.study.module.user.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.study.common.handler.exception.NotFoundUserException;
import com.study.module.user.application.port.input.*;
import com.study.module.user.application.port.output.UserFindPort;
import com.study.module.user.application.port.output.UserModifyPort;
import com.study.module.user.domain.UserDomain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService implements JwtFindQuery, JwtModifyUseCase, JwtRegisterUseCase, JwtRemoveUseCase, JwtSendUseCase {

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

    private final UserFindPort userFindPort;
    private final UserModifyPort userModifyPort;

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

    @Override
    public void sendAccessTokenAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        setHeader(response, accessTokenHeader, accessToken);
        setHeader(response, refreshTokenHeader, refreshToken);

    }

    @Override
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        setHeader(response, accessTokenHeader, accessToken);
    }

    @Override
    public void sendRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        setHeader(response, refreshTokenHeader, refreshToken);
    }

    @Override
    public void updateRefreshToken(String userId, String refreshToken) {
        UserDomain userDomain = userFindPort.findByUserId(userId);
        if (userDomain == null) {
            throw new NotFoundUserException(userId);
        }

        userModifyPort.updateRefreshToekn(userDomain, refreshToken);

    }

    @Override
    public void destroyRefreshToken(String userId) {
        updateRefreshToken(userId, null);
    }

    @Override
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessTokenHeader)).filter(
                accessToken -> accessToken.startsWith(BEARER)
        ).map(accessToken -> accessToken.replace(BEARER, ""));
    }

    @Override
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshTokenHeader)).filter(
                refreshToken -> refreshToken.startsWith(BEARER)
        ).map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    @Override
    public Optional<String> extractUserId(String accessToken) {
        return Optional.ofNullable(
                JWT.require(Algorithm.HMAC512(secretKey)).build().verify(accessToken).getClaim(USERNAME_CLAIM)
                        .asString()
        );
    }

    @Override
    public Boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void setHeader(HttpServletResponse response, String key,  String value) {
        response.setHeader(key, value);
    }
}
