package com.study.module.user.application.service;

import com.study.common.handler.exception.NotExistUser;
import com.study.module.auth.application.service.JwtTokenProvider;
import com.study.module.auth.domain.JwtToken;
import com.study.module.user.application.port.input.UserFindQuery;
import com.study.module.user.application.port.input.UserModifyUseCase;
import com.study.module.user.application.port.input.UserRegisterUseCase;
import com.study.module.user.application.port.input.UserRemoveUseCase;
import com.study.module.user.application.port.input.command.UserLoginCommand;
import com.study.module.user.application.port.input.command.UserRegisterCommand;
import com.study.module.user.application.port.output.UserModifyPort;
import com.study.module.user.application.port.output.UserRegisterPort;
import com.study.module.user.application.port.output.UserFindPort;
import com.study.module.user.application.port.output.UserRemovePort;
import com.study.module.user.domain.ExternalUserDomain;
import com.study.module.user.domain.UserDetailsImpl;
import com.study.module.user.domain.UserDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserFindQuery, UserRegisterUseCase, UserRemoveUseCase, UserModifyUseCase {
    private final UserRegisterPort userRegisterPort;
    private final UserModifyPort userModifyPort;
    private final UserFindPort userFindPort;
    private final UserRemovePort userRemovePort;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

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
    public JwtToken findLoginIinfo(UserLoginCommand command) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(command.userId(), command.password());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public void save(UserRegisterCommand command) {
        String encodePassword = passwordEncoder.encode(command.password());
        userRegisterPort.save(UserDomain.of(command, encodePassword));
    }
}
