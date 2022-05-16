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

    @Query(nativeQuery = true, value = "SELECT u.id, u.name, u.nickname, u.email from users u INNER JOIN subscriptions s ON u.id = s.user_id WHERE chatId = s.chat_id")
    User[] getUsersByChatId(Long chatId);
}
