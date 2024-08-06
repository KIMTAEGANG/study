package com.study.module.auth.application.port.output;

import com.study.module.auth.domain.TokenInfo;

public interface JwtPort {
    TokenInfo findByRefreshToken(String refreshToken);
}
