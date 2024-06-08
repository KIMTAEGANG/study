package com.study.module.user.application.port.output;

import com.study.module.user.domain.UserDomain;

public interface UserRemovePort {
    void removeRefreshToken(UserDomain userDomain, String refreshToken);
}
