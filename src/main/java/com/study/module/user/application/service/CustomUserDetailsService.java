package com.study.module.user.application.service;

import com.study.module.user.application.port.output.UserFindPort;
import com.study.module.user.domain.UserDetailsImpl;
import com.study.module.user.domain.UserDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserFindPort userFindPort;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserDomain userDomain = userFindPort.findByUserId(userId);
        if(userDomain == null) {
            return null;
        }
        return new UserDetailsImpl(userDomain);
    }
}
