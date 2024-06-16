package com.study.module.user.application.service;

import com.study.common.handler.exception.NotExistUser;
import com.study.module.user.application.port.input.UserFindQuery;
import com.study.module.user.application.port.input.UserModifyUseCase;
import com.study.module.user.application.port.input.UserRegisterUseCase;
import com.study.module.user.application.port.input.UserRemoveUseCase;
import com.study.module.user.application.port.output.UserModifyPort;
import com.study.module.user.application.port.output.UserRegisterPort;
import com.study.module.user.application.port.output.UserFindPort;
import com.study.module.user.application.port.output.UserRemovePort;
import com.study.module.user.domain.ExternalUserDomain;
import com.study.module.user.domain.UserDetailsImpl;
import com.study.module.user.domain.UserDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserFindQuery, UserRegisterUseCase, UserRemoveUseCase, UserModifyUseCase, UserDetailsService {
    private final UserRegisterPort userRegisterPort;
    private final UserModifyPort userModifyPort;

    private final UserFindPort userFindPort;
    private final UserRemovePort userRemovePort;

    @Override
    public ExternalUserDomain findOne(String userId) {
        UserDomain userDomain = userFindPort.findByUserId(userId);
        if(userDomain == null) {
            throw new NotExistUser();
        }
        return ExternalUserDomain.of(userDomain);
    }

    @Override
    public List<String> findUserIdByEmail(String email) {
        List<UserDomain> userDomainList = userFindPort.findUserIdByEmail(email);
        if(CollectionUtils.isEmpty(userDomainList)) {
            throw new NotExistUser();
        }

        return userDomainList.stream().map(UserDomain::userId).toList();
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserDomain userDomain = userFindPort.findByUserId(userId);
        if(userDomain == null) {
            throw new NotExistUser();
        }
        return new UserDetailsImpl(userDomain);
    }
}
