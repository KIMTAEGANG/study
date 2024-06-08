package com.study.module.user.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.study.common.exception.NotFoundUserException;
import com.study.module.user.application.port.input.UserFindQuery;
import com.study.module.user.application.port.input.UserModifyUseCase;
import com.study.module.user.application.port.input.UserRegisterUseCase;
import com.study.module.user.application.port.input.UserRemoveUseCase;
import com.study.module.user.application.port.output.UserModifyPort;
import com.study.module.user.application.port.output.UserRegisterPort;
import com.study.module.user.application.port.output.UserFindPort;
import com.study.module.user.application.port.output.UserRemovePort;
import com.study.module.user.domain.ExternalUserDomain;
import com.study.module.user.domain.UserDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserFindQuery, UserRegisterUseCase, UserRemoveUseCase, UserModifyUseCase {

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
    private final UserRegisterPort userRegisterPort;
    private final UserModifyPort userModifyPort;

    private final UserFindPort userFindPort;
    private final UserRemovePort userRemovePort;

    @Override
    public ExternalUserDomain findOne(String userId) {
        UserDomain userDomain = userFindPort.findByUserId(userId);
        return ExternalUserDomain.of(userDomain);
    }

    @Override
    public List<String> findUserIdByEmail(String email) {
        List<String> userId = null;
        try {
            userId = userFindPort.findUserIdByEmail(email).stream().map(ExternalUserDomain::userId).toList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return userId;
    }

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
    public void updateRefreshToken(String userId, String refreshToken) {
        UserDomain userDomain = userFindPort.findByUserId(userId);
        if(userDomain == null) {
            throw new NotFoundUserException(userId);
        }

        userModifyPort.updateRefreshToekn(userDomain, refreshToken);

    }

    @Override
    public void destroyRefreshToken(String userId) {
        updateRefreshToken(userId, null);
    }
}
