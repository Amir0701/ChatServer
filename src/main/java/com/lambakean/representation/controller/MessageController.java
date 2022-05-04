package com.lambakean.representation.controller;

import com.lambakean.domain.service.MessageService;
import com.lambakean.representation.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageDto> create(@RequestBody MessageDto messageDto){
        return ResponseEntity.ok(messageService.create(messageDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable Long id){
        return ResponseEntity.ok(messageService.delete(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageDto> get(@PathVariable Long id){
        return ResponseEntity.ok(messageService.getById(id));
    }

}
