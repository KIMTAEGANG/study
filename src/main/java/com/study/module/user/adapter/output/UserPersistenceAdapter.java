package com.study.module.user.adapter.output;

import com.study.jpa.entity.UserEntity;
import com.study.jpa.mapper.UserMapper;
import com.study.module.user.application.port.output.UserFindPort;
import com.study.module.user.application.port.output.UserModifyPort;
import com.study.module.user.application.port.output.UserRegisterPort;
import com.study.module.user.application.port.output.UserRemovePort;
import com.study.module.user.domain.ExternalUserDomain;
import com.study.module.user.domain.UserDomain;
import com.study.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserFindPort, UserRegisterPort, UserModifyPort, UserRemovePort {
    private final UserRepository userRepository;
    @Override
    public UserDomain findByUserId(String userId) {
        return userRepository.findByUserId(userId).map(UserMapper.toDomain).orElse(null);
    }

    @Override
    public List<UserDomain> findUserIdByEmail(String userId) {
        List<UserEntity> list = userRepository.findUserIdByEmail(userId);
        if(CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return UserMapper.toDomainList.apply(list);
    }

    @Override
    public boolean existsByUserId(String userId) {
        return false;
    }

    @Override
    public void save(UserDomain userDomain) {
        UserEntity entity = UserMapper.toEntity(userDomain);
        userRepository.save(entity);
    }
}
