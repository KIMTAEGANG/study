package com.study.common.provider;

import com.study.common.handler.exception.LoginCheckFailException;
import com.study.module.user.application.service.CustomUserDetailsService;
import com.study.module.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = authentication.getName();
        UserDetails userDetails = userService.loadUserByUsername(userId);

        String password = authentication.getCredentials().toString();
        String hashPassword = userDetails.getPassword();
        checkPassword(password, hashPassword);
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }


    private void checkPassword(String password, String hashPassword) {
        boolean isPasswordMatch = passwordEncoder.matches(password, hashPassword);
        if(!isPasswordMatch) {
            throw new LoginCheckFailException();
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
