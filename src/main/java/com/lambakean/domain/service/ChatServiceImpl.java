package com.lambakean.domain.service;

import com.lambakean.data.model.*;
import com.lambakean.data.repository.ChatRepository;
import com.lambakean.data.repository.ImageRepository;
import com.lambakean.data.repository.SubscriptionRepository;
import com.lambakean.domain.exception.InvalidEntityException;
import com.lambakean.domain.exception.UserNotLoggedInException;
import com.lambakean.representation.dto.ChatDto;
import com.lambakean.representation.dto.UserDto;
import com.lambakean.representation.dtoConverter.ChatDtoConverter;
import com.lambakean.representation.dtoConverter.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ChatServiceImpl implements ChatService {

    private final ChatDtoConverter chatDtoConverter;
    private final ChatRepository chatRepository;
    private final UserService userService;
    private final UserDtoConverter userDtoConverter;
    private final SubscriptionRepository subscriptionRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public ChatServiceImpl(ChatDtoConverter chatDtoConverter,
                           ChatRepository chatRepository,
                           UserService userService,
                           UserDtoConverter userDtoConverter,
                           SubscriptionRepository subscriptionRepository,
                           ImageRepository imageRepository) {
        this.chatDtoConverter = chatDtoConverter;
        this.chatRepository = chatRepository;
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.subscriptionRepository = subscriptionRepository;
        this.imageRepository = imageRepository;
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
        //User user = userService.getCurrentUser();
        Chat chat = chatRepository.getById(id);
        ChatDto chatDto = chatDtoConverter.toChatDto(chat);
        chatRepository.deleteById(id);
        return chatDto;
    }

    @Override
    public ChatDto get(Long id) {
        Chat currentChat = chatRepository.getById(id);
        ChatDto chatDto = chatDtoConverter.toChatDto(currentChat);
        return chatDto;
    }

    @Override
    public ChatDto[] getChatsByUserId(Long userId) {
        Chat[] chats = chatRepository.getChatsByUserId(userId);
        for (Chat chat : chats){
            Set<Message> set = chat.getMessages();
            for (Message message : set){
                Image[] images = imageRepository.getImagesByMessageId(message.getId());
                message.setImages(Arrays.stream(images).collect(Collectors.toSet()));
            }
        }

        ChatDto[] chatDtos = new ChatDto[chats.length];

        for (int i = 0; i < chats.length; i++){
            ChatDto chatDto = chatDtoConverter.toChatDto(chats[i]);
            chatDtos[i] = chatDto;
        }
        return chatDtos;
    }

    @Override
    public ChatDto put(Long id, UserDto[] userDtos) {
        Chat currentChat = chatRepository.getById(id);
        User currentUser = userService.getCurrentUser();

        for (UserDto userDto: userDtos){
            if (userDto.getId().equals(currentUser.getId())) continue;

            User user = userDtoConverter.toUser(userDto);
            Subscription subscription = new Subscription();
            subscription.setChat(currentChat);
            subscription.setUser(user);
            subscription.setUserRole(Role.MEMBER);
            subscriptionRepository.saveAndFlush(subscription);
        }
        ChatDto chatDto = chatDtoConverter.toChatDto(currentChat);
        return chatDto;
    }

    @Override
    public ChatDto[] getAllChats() {
        Chat[] chats = chatRepository.getAllChats();
        ChatDto[] chatDtos = new ChatDto[chats.length];

        for (int i = 0; i < chats.length; i++){
            chatDtos[i] = chatDtoConverter.toChatDto(chats[i]);
        }
        return chatDtos;
    }


    @Async
    public void saveAndFlush(Chat chat) {
        chatRepository.saveAndFlush(chat);
    }
}
