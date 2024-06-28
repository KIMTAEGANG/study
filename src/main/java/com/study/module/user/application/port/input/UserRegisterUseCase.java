package com.study.module.user.application.port.input;

import com.study.module.user.application.port.input.command.UserRegisterCommand;

public interface UserRegisterUseCase {
    void save(UserRegisterCommand command);

}
