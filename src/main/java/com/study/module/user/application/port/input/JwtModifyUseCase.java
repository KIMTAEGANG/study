package com.study.module.user.application.port.input;

public interface JwtModifyUseCase {
    void updateRefreshToken(String userId, String refreshToken);
}
