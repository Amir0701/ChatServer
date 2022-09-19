package com.lambakean.data.repository;

import com.lambakean.data.model.Chat;
import com.lambakean.data.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM  messages m WHERE m.chat_id = :chatId ORDER BY m.when_created DESC LIMIT 50")
    Message[] getMessagesByChatId(Long chatId);
}
