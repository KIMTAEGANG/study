package com.study.common.handler;

import com.study.module.user.application.port.input.JwtModifyUseCase;
import com.study.module.user.application.port.input.JwtRegisterUseCase;
import com.study.module.user.application.port.input.UserFindQuery;
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
    private final JwtRegisterUseCase jwtRegisterUseCase;
    private final JwtModifyUseCase jwtModifyUseCase;
    private final UserFindQuery userFindQuery;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String userId = extractUserId(authentication);
        String accessToken = jwtRegisterUseCase.createAccessToken(userId);
        String refreshToken = jwtRegisterUseCase.createRefreshToken();
        jwtRegisterUseCase.sendAccessTokenAndRefreshToken(response, accessToken, refreshToken);

        ExternalUserDomain externalUserDomain = userFindQuery.findOne(userId);
        jwtModifyUseCase.updateRefreshToken(userId, refreshToken);

        response.getWriter().write("success");
    }

    private String extractUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
