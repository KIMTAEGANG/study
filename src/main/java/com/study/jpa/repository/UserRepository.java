package com.study.jpa.repository;

import com.study.jpa.entity.UserEntity;
import com.study.jpa.repository.custom.CustomUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String>, CustomUserRepository {
    Optional<UserEntity> findByUserId(String userId);
    List<UserEntity> findUserIdByEmail(String userId);
}
