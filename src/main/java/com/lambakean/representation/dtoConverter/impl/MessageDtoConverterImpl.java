package com.lambakean.representation.dtoConverter.impl;

import com.lambakean.data.model.Chat;
import com.lambakean.data.model.Message;
import com.lambakean.data.model.User;
import com.lambakean.representation.dto.MessageDto;
import com.lambakean.representation.dtoConverter.MessageDtoConverter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class MessageDtoConverterImpl implements MessageDtoConverter {

    @Override
    public Message toMessage(@NonNull MessageDto messageDto) {

        Message message = new Message();

        message.setId(messageDto.getId());
        message.setChat(new Chat(messageDto.getChatId()));
        message.setUser(new User(messageDto.getUserId()));
        message.setWhenCreated(messageDto.getWhenCreated());
        return message;
    }

    @Override
    public MessageDto toMessageDto(@NonNull Message message) {

        MessageDto messageDto = new MessageDto();

        messageDto.setId(message.getId());
        messageDto.setChatId(message.getChatId());
        messageDto.setUserId(message.getUserId());
        messageDto.setWhenCreated(message.getWhenCreated());

        return messageDto;
    }
}
