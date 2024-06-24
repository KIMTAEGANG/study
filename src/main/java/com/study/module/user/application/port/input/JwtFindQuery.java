package com.study.module.user.application.port.input;

import com.study.module.user.domain.UserDomain;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface JwtFindQuery{
    Optional<String> extractAccessToken(HttpServletRequest request);
    Optional<String> extractRefreshToken(HttpServletRequest request);
    Optional<String> extractUserId(String accessToken);
    Boolean isTokenValid(String token);
}
