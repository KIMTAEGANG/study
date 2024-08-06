package com.study.jpa.mapper;

import com.study.jpa.entity.TokenInfoEntity;
import com.study.module.auth.domain.TokenInfo;

import java.util.function.Function;

public interface TokenInfoMapper {
    Function<TokenInfoEntity, TokenInfo> toDomain = (entity) -> new TokenInfo(
            entity.getUserId(),
            entity.getRefreshToken()
    );
}
