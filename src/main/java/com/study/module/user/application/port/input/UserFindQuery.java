package com.study.module.user.application.port.input;

import com.study.module.auth.domain.JwtToken;
import com.study.module.user.application.port.input.command.UserLoginCommand;
import com.study.module.user.domain.ExternalUserDomain;
import com.study.module.user.domain.UserDomain;

import java.util.List;

public interface UserFindQuery {
    ExternalUserDomain findOne(String userId);
    List<String> findUserIdByEmail(String email);
    JwtToken findLoginIinfo(UserLoginCommand command);
}
