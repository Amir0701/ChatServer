package com.lambakean.data.repository;

import com.lambakean.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(String name);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);
}
