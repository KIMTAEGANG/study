package com.study.jpa.mapper;

import com.study.common.utils.ObjectUtils;
import com.study.jpa.entity.UserEntity;
import com.study.module.user.domain.ExternalUserDomain;
import com.study.module.user.domain.UserDomain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public interface UserMapper {
    Function<UserEntity, UserDomain> toDomain = (c) -> new UserDomain(
            c.getUserId(),
            c.getPassword(),
            c.getUserName(),
            c.getEmail(),
            c.getRefreshToken(),
            c.getCreateDate(),
            c.getModifyDate()
    );

    Function<List<UserEntity>, List<ExternalUserDomain>> toDomainList = (c) -> c.stream()
            .map(
                    d -> new ExternalUserDomain(
                            d.getUserId(),
                            d.getUserName(),
                            d.getEmail(),
                            d.getRefreshToken(),
                            d.getCreateDate(),
                            d.getModifyDate()
                    )
            ).toList();

    static UserEntity toEntity(UserDomain userDomain, String refreshToken) {
        return new UserEntity(
                userDomain.userId(),
                userDomain.password(),
                userDomain.userName(),
                userDomain.email(),
                refreshToken,
                ObjectUtils.ifNull(userDomain.createDate(), LocalDateTime.now()),
                userDomain.createDate() != null ? LocalDateTime.now() : userDomain.modifyDate()
        );
    }
}
