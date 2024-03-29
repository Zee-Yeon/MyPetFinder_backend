package com.finder.mypet.user.domain.repository;

import com.finder.mypet.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByNickname(String nickname);
    Optional<User> deleteByUserId(String userId);
    Optional<User> findByPassword(String password);
}
