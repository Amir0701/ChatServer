package com.lambakean.data.repository;

import com.lambakean.data.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query(nativeQuery = true, value = "SELECT ch.id, ch.name, ch.when_created FROM chats ch INNER JOIN subscriptions s ON ch.id = s.chat_id WHERE :userId = s.user_id")
    Chat[] getChatsByUserId(Long userId);
}