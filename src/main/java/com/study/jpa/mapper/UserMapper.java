package com.study.jpa.mapper;

import com.study.jpa.entity.UserEntity;
import com.study.module.user.domain.UserDomain;

import java.util.List;
import java.util.function.Function;

public interface UserMapper {
    Function<UserEntity, UserDomain> toDomain = (c) -> new UserDomain(
            c.getUserId(),
            c.getUserName(),
            c.getEmail()
    );

    Function<List<UserEntity>, List<UserDomain>> toDomainList = (c) -> c.stream()
            .map(
                    d -> new UserDomain(d.getUserId(), d.getUserName(), d.getEmail())
            ).toList();
}
