package com.study.module.user.application.service;

import com.study.module.user.application.port.input.UserFindQuery;
import com.study.module.user.application.port.output.UserFindPort;
import com.study.module.user.domain.UserDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserFindQuery {

    private final UserFindPort userFindPort;

    @Override
    public UserDomain findOne(String userId) {
        return userFindPort.findByUserId(userId);
    }
}
