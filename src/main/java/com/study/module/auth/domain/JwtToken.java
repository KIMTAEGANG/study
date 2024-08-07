package com.study.module.auth.domain;

public record JwtToken(
        String grantType,
        String accessToken,
        String refreshToken
) {
}
