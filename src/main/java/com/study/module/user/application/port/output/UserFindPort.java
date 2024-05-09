package com.study.module.user.application.port.output;

import com.study.module.user.domain.UserDomain;

import java.util.List;

public interface UserFindPort {
    UserDomain findByUserId(String userId);
    List<UserDomain> findUserId(String email);
}
