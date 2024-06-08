package com.study.module.user.application.port.input;

public interface UserModifyUseCase {
    void updateRefreshToken(String userId, String refreshToken);
}
