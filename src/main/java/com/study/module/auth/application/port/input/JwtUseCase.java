package com.study.module.auth.application.port.input;

import com.study.module.auth.domain.TokenInfo;

public interface JwtUseCase {
    TokenInfo findByRefreshToken(String refreshToken);
    String createAccessToken(String userId);
    String createRefreshToken(String userId);
    String reCreateAccessToken(String refreshToken) throws Exception;
}
