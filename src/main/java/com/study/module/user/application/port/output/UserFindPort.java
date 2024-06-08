package com.study.module.user.application.port.output;

import com.study.module.user.domain.ExternalUserDomain;
import com.study.module.user.domain.UserDomain;

import java.util.List;

public interface UserFindPort {
    UserDomain findByUserId(String userId);
    List<ExternalUserDomain> findUserIdByEmail(String email);
    boolean existsByUserId(String userId);
    UserDomain findByRefreshToken(String refreshToken);
}
