package com.lambakean.domain.service;

import com.lambakean.representation.dto.ChatDto;
import org.springframework.validation.BindingResult;

import java.util.concurrent.CompletableFuture;

public interface ChatService {

    CompletableFuture<ChatDto> create(ChatDto chatData, BindingResult bindingResult);

}
