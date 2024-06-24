package com.study.module.user.application.port.input;

import jakarta.servlet.http.HttpServletResponse;

public interface JwtSendUseCase {
    void sendAccessToken(HttpServletResponse response, String accessToken);
    void sendRefreshToken(HttpServletResponse response, String refreshToken);
}
