package com.study.module.user.application.port.input;

import com.study.module.user.domain.UserDomain;

import java.util.List;

public interface UserFindQuery {
    UserDomain findOne(String userId);
    List<String> findUserId(String email);
}
