package com.lambakean.representation.controller;

import com.lambakean.domain.service.ChatService;
import com.lambakean.representation.dto.ChatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatDto> create(@RequestBody @Valid ChatDto chatData, BindingResult bindingResult)
            throws ExecutionException, InterruptedException {

        ChatDto createdChat = chatService.create(chatData, bindingResult).get();

        return new ResponseEntity<>(createdChat, HttpStatus.CREATED);
    }

}
