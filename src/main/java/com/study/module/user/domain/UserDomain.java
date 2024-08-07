package com.study.module.user.domain;

import com.study.module.user.application.port.input.command.UserRegisterCommand;

import java.time.LocalDateTime;

public record UserDomain(
        String userId,
        String password,
        String userName,
        String email,
        String role,
        LocalDateTime createDate,
        LocalDateTime modifyDate
) {
    public static UserDomain of(UserRegisterCommand command, String encodePassword) {
        return new UserDomain(
                command.userId(),
                encodePassword,
                command.userName(),
                command.email(),
                "USER",
                LocalDateTime.now(),
                null
        );

    }
}
