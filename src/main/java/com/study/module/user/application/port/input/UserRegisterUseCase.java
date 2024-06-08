package com.study.module.user.application.port.input;

public interface UserRegisterUseCase {
    String createAccessToken(String userId);
    String createRefreshToken();
}
