package com.study.module.user.domain;

import java.time.LocalDateTime;

public record UserDomain(
        String userId,
        String password,
        String userName,
        String email,
        String refreshToken,
        LocalDateTime createDate,
        LocalDateTime modifyDate
) {
}
