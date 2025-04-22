package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // 리포지토리 가져옴.

    /**
     * user 를 생성, 저장합니다.
     *
     * @param userEntity
     * @return userRepository.save(userEntity)
     */
    public UserEntity create(final UserEntity userEntity) {
        // entity가 null인지 검사. 없으면 에러 리턴
        if (userEntity == null || userEntity.getUsername() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        // 유저네임 가져오기
        final String username = userEntity.getUsername();
        // 유저네임이 존재하는지 확인한다. 이미 있는 경우 true가 된다.
        if (userRepository.existsByUsername(username)) {
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }

        // user repository로 db에 저장한다.
        return userRepository.save(userEntity);
    }

    // username, password로 유저 엔티티. 사용자 정보를 가져온다.
    public UserEntity getByCredentials(final String username, final String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
