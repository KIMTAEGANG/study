package com.study.module.user.adapter.output;

import com.study.jpa.entity.UserEntity;
import com.study.jpa.mapper.UserMapper;
import com.study.module.user.application.port.output.UserFindPort;
import com.study.module.user.domain.UserDomain;
import com.study.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserFindPort {
    private final UserRepository userRepository;
    @Override
    public UserDomain findByUserId(String userId) {
        return userRepository.findByUserId(userId).map(UserMapper.toDomain).orElse(null);
    }

    @Override
    public List<UserDomain> findUserId(String email) {
        List<UserEntity> list = userRepository.findUserIdByEmail(email);
        if(CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return UserMapper.toDomainList.apply(list);
    }
}
