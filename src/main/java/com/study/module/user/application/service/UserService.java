package com.study.module.user.application.service;

import com.study.module.user.application.port.input.UserFindQuery;
import com.study.module.user.application.port.output.UserFindPort;
import com.study.module.user.domain.UserDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserFindQuery {

    private final UserFindPort userFindPort;

    @Override
    public UserDomain findOne(String userId) {
        return userFindPort.findByUserId(userId);
    }

    @Override
    public List<String> findUserId(String email) {
        List<String> userId = null;
        try {
            userId = userFindPort.findUserId(email).stream().map(UserDomain::userId).toList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return userId;
    }
}
