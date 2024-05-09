package com.study.jpa.repository.custom.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.jpa.entity.UserEntity;
import com.study.jpa.repository.custom.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.study.jpa.entity.QUserEntity.userEntity;

@Component
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserEntity> findUserIdByEmail(String email) {
        return queryFactory
                .selectFrom(
                        userEntity
                )
                .where(
                        userEntity.email.eq(email)
                )
                .fetch();

    }
}
