package com.study.module.auth.domain;

public record TokenInfo(
        String userId,
        String refreshToken
) {
}
