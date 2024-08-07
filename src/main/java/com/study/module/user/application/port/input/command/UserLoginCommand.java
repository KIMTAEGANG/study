package com.study.module.user.application.port.input.command;

public record UserLoginCommand(
        String userId,
        String password
) {
}
