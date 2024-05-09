package com.study.jpa.repository.custom;

import com.study.jpa.entity.UserEntity;

import java.util.List;

public interface CustomUserRepository {
    List<UserEntity> findUserIdByEmail(String email);
}
