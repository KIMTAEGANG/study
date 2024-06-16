package com.study.module.user.application.port.input;

public interface JwtRemoveUseCase {
    void destroyRefreshToken(String userId);
}
