package com.study.module.user.application.port.input.command;

public record UserRegisterCommand(
        String userId,
        String password,
        String email,
        String userName
) {
}
