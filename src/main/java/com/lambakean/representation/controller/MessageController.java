package com.lambakean.representation.controller;

import com.lambakean.domain.service.MessageService;
import com.lambakean.representation.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageDto> create(@RequestBody MessageDto messageDto){
        return ResponseEntity.ok(messageService.create(messageDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable Long id){
        return ResponseEntity.ok(messageService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> get(@PathVariable Long id){
        return ResponseEntity.ok(messageService.getById(id));
    }

    @GetMapping(params = "chatId")
    public ResponseEntity<MessageDto[]> getMessagesByChatId(@RequestParam Long chatId){
        return ResponseEntity.ok(messageService.getMessagesByChatId(chatId));
    }

    @PostMapping("/upload")
    public ResponseEntity<MessageDto> message(@RequestParam(value = "files") MultipartFile[] multipartFile,
                                              @RequestParam(value = "chatId") Long chatId,
                                              @RequestParam(value = "userId") Long userId) {
        return ResponseEntity.ok(messageService.add(multipartFile, chatId, userId));
    }

    @MessageMapping("/send")
    @SendTo("/topic/send")
    public ResponseEntity<MessageDto> send(@RequestParam MessageDto messageDto){
        return ResponseEntity.ok(messageDto);
    }
}
