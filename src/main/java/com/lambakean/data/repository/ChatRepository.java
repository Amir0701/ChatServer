package com.lambakean.data.repository;

import com.lambakean.data.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {}