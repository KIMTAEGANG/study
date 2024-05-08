package com.study.module.user.application.port.input;

import com.study.module.user.domain.UserDomain;

public interface UserFindQuery {
    UserDomain findOne(String userId);
}
