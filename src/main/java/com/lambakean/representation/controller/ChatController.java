package com.lambakean.representation.controller;

import com.lambakean.domain.service.ChatService;
import com.lambakean.representation.dto.ChatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatDto> create(@RequestBody @Valid ChatDto chatData, BindingResult bindingResult) {

        ChatDto createdChat = chatService.create(chatData, bindingResult);

        return new ResponseEntity<>(createdChat, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ChatDto> delete(@PathVariable Long id){
        return ResponseEntity.ok(chatService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatDto> getChat(@PathVariable Long id){
        return ResponseEntity.ok(chatService.get(id));
    }

//    public ResponseEntity<ChatDto> update(@RequestBody @Valid ChatDto chatDto, BindingResult bindingResult){
//        return ResponseEntity.ok(chatService.update(chatDto, bindingResult));
//    }

    @GetMapping(params = "userId")
    public ResponseEntity<ChatDto[]> getChatsByUserId(@RequestParam Long userId){
        return ResponseEntity.ok(chatService.getChatsByUserId(userId));
    }

}
