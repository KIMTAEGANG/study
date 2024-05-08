package com.study.module.user.application.port.output;

import com.study.module.user.domain.UserDomain;

public interface UserFindPort {
    UserDomain findByUserId(String userId);
}
