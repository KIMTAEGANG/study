package com.study.module.user.application.service;

import com.study.common.handler.exception.NotExistUser;
import com.study.module.user.application.port.input.UserFindQuery;
import com.study.module.user.application.port.output.UserFindPort;
import com.study.module.user.domain.UserDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserFindQuery {

    private final UserFindPort userFindPort;

    @Override
    public UserDomain findOne(String userId) {
        UserDomain userDomain = userFindPort.findByUserId(userId);
        if(userDomain == null) {
            throw new NotExistUser();
        }
        return userDomain;
    }

    @Override
    public List<String> findUserId(String email) {
        List<UserDomain> userDomainList = userFindPort.findUserId(email);
        if(CollectionUtils.isEmpty(userDomainList)) {
            throw new NotExistUser();
        }

        return userDomainList.stream().map(UserDomain::userId).toList();
    }
}
