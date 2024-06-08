package com.study.module.user.application.port.input;

public interface UserRemoveUseCase {
    void destroyRefreshToken(String userId);

}
