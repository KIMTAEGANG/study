package com.study.module.user.application.port.input;

public interface JwtRegisterUseCase {
    String createAccessToken(String userId);
    String createRefreshToken();
}
