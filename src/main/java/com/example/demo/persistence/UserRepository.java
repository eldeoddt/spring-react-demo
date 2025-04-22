package com.example.demo.persistence;

import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// <저장할 엔티티, pk의 타입>
public interface UserRepository extends JpaRepository<UserEntity, String> {
    // findByUsername은 자동 생성 안된다.
    UserEntity findByUsername(String username);

    Boolean existsByUsername(String username);

    UserEntity findByUsernameAndPassword(String username, String password);
}
