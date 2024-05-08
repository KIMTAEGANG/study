package com.study.jpa.mapper;

import com.study.jpa.entity.UserEntity;
import com.study.module.user.domain.UserDomain;

import java.util.function.Function;

public interface UserMapper {
    Function<UserEntity, UserDomain> toDomain = (c) -> new UserDomain(
            c.getUserId(),
            c.getUserName(),
            c.getEmail()
    );
}
