package com.lambakean.data.repository;

import com.lambakean.data.model.Chat;
import com.lambakean.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(String name);

    User findByNickname(String nickname);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * from users u where u.id=:id")
    Chat[] getChatsByUserId(Long id);
}
