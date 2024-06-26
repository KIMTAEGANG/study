package com.study.module.user.domain;

import java.time.LocalDateTime;

public record ExternalUserDomain(
        String userId,
        String userName,
        String email,
        LocalDateTime createDate,
        LocalDateTime modifyDate
) {
    public static ExternalUserDomain of(UserDomain userDomain) {
        return new ExternalUserDomain(
                userDomain.userId(),
                userDomain.userName(),
                userDomain.email(),
                userDomain.createDate(),
                userDomain.modifyDate()
        );
    }
}
