package com.study.module.user.application.port.input;

import jakarta.servlet.http.HttpServletResponse;

public interface JwtRegisterUseCase {
    String createAccessToken(String userId);
    String createRefreshToken();
    void sendAccessTokenAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken);
    void sendAccessToken(HttpServletResponse response, String accessToken);
    void sendRefreshToken(HttpServletResponse response, String refreshToken);
}
