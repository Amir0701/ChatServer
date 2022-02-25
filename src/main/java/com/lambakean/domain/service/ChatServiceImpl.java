package com.lambakean.domain.service;

import com.lambakean.representation.dto.ChatDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.concurrent.CompletableFuture;

@Component
public class ChatServiceImpl implements ChatService {

    @Override
    public CompletableFuture<ChatDto> create(ChatDto chatData, BindingResult bindingResult) {
        return null;
    }
}
