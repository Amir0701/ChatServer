package com.lambakean.domain.service;

import com.lambakean.data.model.Chat;
import com.lambakean.data.model.Role;
import com.lambakean.data.model.Subscription;
import com.lambakean.data.model.User;
import com.lambakean.data.repository.ChatRepository;
import com.lambakean.domain.exception.InvalidEntityException;
import com.lambakean.domain.exception.UserNotLoggedInException;
import com.lambakean.representation.dto.ChatDto;
import com.lambakean.representation.dtoConverter.ChatDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class ChatServiceImpl implements ChatService {

    private final ChatDtoConverter chatDtoConverter;
    private final ChatRepository chatRepository;
    private final UserService userService;

    @Autowired
    public ChatServiceImpl(ChatDtoConverter chatDtoConverter, ChatRepository chatRepository, UserService userService) {
        this.chatDtoConverter = chatDtoConverter;
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    @Override
    public ChatDto create(ChatDto chatData, BindingResult bindingResult) {

        User user = userService.getCurrentUser();

        if(user == null) {
            throw new UserNotLoggedInException("You have to log in to create chats");
        }

        if(bindingResult.hasErrors()) {
            throw new InvalidEntityException(
                    bindingResult.getFieldErrors()
                            .stream()
                            .map(FieldError::getDefaultMessage)
                            .collect(Collectors.toSet())
            );
        }

        Chat chat = chatDtoConverter.toChat(chatData);

        Subscription subscription = new Subscription();
        subscription.setChat(chat);
        subscription.setUser(user);
        subscription.setUserRole(Role.CREATOR);

        chat.setId(null);
        chat.setWhenCreated(LocalDateTime.now());
        chat.setMessages(Collections.emptySet());
        chat.setSubscriptions(Collections.singleton(subscription));

        saveAndFlush(chat);

        return chatDtoConverter.toChatDto(chat);
    }

    @Override
    public ChatDto delete(Long id) {
        User user = userService.getCurrentUser();
        Chat chat = chatRepository.getById(id);
        chatRepository.deleteById(id);
        ChatDto chatDto = chatDtoConverter.toChatDto(chat);
        return chatDto;
    }

    @Override
    public ChatDto get(Long id) {
        Chat currentChat = chatRepository.getById(id);
        ChatDto chatDto = chatDtoConverter.toChatDto(currentChat);
        return chatDto;
    }

//    @Override
//    public ChatDto update(ChatDto chatDto, BindingResult bindingResult) {
//        if(bindingResult.hasErrors()) {
//            throw new InvalidEntityException(
//                    bindingResult.getFieldErrors().stream()
//                            .map(FieldError::getDefaultMessage)
//                            .collect(Collectors.toSet())
//            );
//        }
//
//        chatRepository.save(chatDto);
//        return chatDto;
//    }

    @Async
    public void saveAndFlush(Chat chat) {
        chatRepository.saveAndFlush(chat);
    }
}
