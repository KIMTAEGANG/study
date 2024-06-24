package com.study.common.handler;

import com.study.module.user.application.port.input.JwtModifyUseCase;
import com.study.module.user.application.port.input.JwtRegisterUseCase;
import com.study.module.user.application.port.input.UserFindQuery;
import com.study.module.user.application.service.JwtService;
import com.study.module.user.application.service.UserService;
import com.study.module.user.domain.ExternalUserDomain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String userId = extractUserId(authentication);
        String accessToken = jwtService.createAccessToken(userId);
        String refreshToken = jwtService.createRefreshToken();
        jwtService.sendAccessTokenAndRefreshToken(response, accessToken, refreshToken);

        ExternalUserDomain externalUserDomain = userService.findOne(userId);
        if(externalUserDomain != null) {
            jwtService.updateRefreshToken(userId, refreshToken);
        }

        response.getWriter().write("success");
    }

    private String extractUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
