package com.lambakean.representation.dtoConverter.impl;

import com.lambakean.data.model.Chat;
import com.lambakean.representation.dto.ChatDto;
import com.lambakean.representation.dtoConverter.ChatDtoConverter;
import com.lambakean.representation.dtoConverter.MessageDtoConverter;
import com.lambakean.representation.dtoConverter.SubscriptionDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class ChatDtoConverterImpl implements ChatDtoConverter {

    private final MessageDtoConverter messageDtoConverter;
    private final SubscriptionDtoConverter subscriptionDtoConverter;

    @Autowired
    public ChatDtoConverterImpl(MessageDtoConverter messageDtoConverter,
                                SubscriptionDtoConverter subscriptionDtoConverter) {
        this.messageDtoConverter = messageDtoConverter;
        this.subscriptionDtoConverter = subscriptionDtoConverter;
    }

    @Override
    public Chat toChat(ChatDto chatDto) {

        Chat chat = new Chat();

        chat.setId(chatDto.getId());
        chat.setName(chatDto.getName());
        chat.setWhenCreated(chatDto.getWhenCreated());

        if(chatDto.getMessages() != null) {
            chat.setMessages(
                    chatDto.getMessages()
                            .stream()
                            .map(messageDtoConverter::toMessage)
                            .collect(Collectors.toSet())
            );
        } else {
            chat.setMessages(new HashSet<>());
        }

        if(chatDto.getSubscriptions() != null) {
            chat.setSubscriptions(
                    chatDto.getSubscriptions()
                            .stream()
                            .map(subscriptionDtoConverter::toSubscription)
                            .collect(Collectors.toSet())
            );
        } else {
            chat.setSubscriptions(new HashSet<>());
        }

        chat.setAvatar(chatDto.getAvatar());
        return chat;
    }

    @Override
    public ChatDto toChatDto(Chat chat) {

        ChatDto chatDto = new ChatDto();

        chatDto.setId(chat.getId());
        chatDto.setName(chat.getName());
        chatDto.setWhenCreated(chat.getWhenCreated());
        chatDto.setMessages(
                chat.getMessages()
                        .stream()
                        .map(messageDtoConverter::toMessageDto)
                        .collect(Collectors.toSet())
        );
        chatDto.setSubscriptions(
                chat.getSubscriptions()
                        .stream()
                        .map(subscriptionDtoConverter::toSubscriptionDto)
                        .collect(Collectors.toSet())
        );

        chatDto.setAvatar(chat.getAvatar());
        return chatDto;
    }
}
