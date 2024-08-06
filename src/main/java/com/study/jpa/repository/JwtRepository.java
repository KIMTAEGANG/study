package com.study.jpa.repository;

import com.study.jpa.entity.TokenInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRepository extends JpaRepository<TokenInfoEntity, String> {
    Optional<TokenInfoEntity> findByRefreshToken(String refreshToken);
}
