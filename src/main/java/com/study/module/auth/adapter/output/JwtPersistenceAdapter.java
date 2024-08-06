package com.study.module.auth.adapter.output;

import com.study.jpa.entity.TokenInfoEntity;
import com.study.jpa.mapper.TokenInfoMapper;
import com.study.jpa.repository.JwtRepository;
import com.study.module.auth.application.port.output.JwtPort;
import com.study.module.auth.domain.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtPersistenceAdapter implements JwtPort {
    private final JwtRepository jwtRepository;
    @Override
    public TokenInfo findByRefreshToken(String refreshToken) {
        Optional<TokenInfoEntity> entity = jwtRepository.findByRefreshToken(refreshToken);
        return entity.map(TokenInfoMapper.toDomain).orElse(null);
    }
}
