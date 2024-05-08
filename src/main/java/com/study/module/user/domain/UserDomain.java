package com.study.module.user.domain;

public record UserDomain(
        String userId,
        String userName,
        String email
) {
}
