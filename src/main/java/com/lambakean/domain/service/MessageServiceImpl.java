package com.lambakean.domain.service;

import com.lambakean.data.model.Image;
import com.lambakean.data.model.Message;
import com.lambakean.data.model.User;
import com.lambakean.data.repository.MessageRepository;
import com.lambakean.domain.exception.InvalidEntityException;
import com.lambakean.domain.exception.UserNotLoggedInException;
import com.lambakean.representation.dto.ImageDto;
import com.lambakean.representation.dto.MessageDto;
import com.lambakean.representation.dtoConverter.MessageDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MessageServiceImpl implements MessageService{
    private MessageDtoConverter messageDtoConverter;
    private MessageRepository messageRepository;
    private UserService userService;
    private ImageService imageService;
    @Autowired
    public MessageServiceImpl(MessageDtoConverter messageDtoConverter,
                              MessageRepository messageRepository,
                              UserService userService,
                              ImageService imageService){
        this.messageDtoConverter = messageDtoConverter;
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.imageService = imageService;
    }


    @Override
    public MessageDto create(MessageDto messageDto) {
        User user = userService.getCurrentUser();

        if(user == null) {
            throw new UserNotLoggedInException("You have to log in to create chats");
        }


        Message newMessage = messageDtoConverter.toMessage(messageDto);
        messageRepository.saveAndFlush(newMessage);
        return messageDto;
    }

    @Override
    public MessageDto delete(Long id) {
        Message message = messageRepository.getById(id);
        messageRepository.deleteById(id);
        MessageDto messageDto = messageDtoConverter.toMessageDto(message);
        return messageDto;
    }

    @Override
    public MessageDto getById(Long id) {
        Message message = messageRepository.getById(id);
        MessageDto messageDto = messageDtoConverter.toMessageDto(message);
        ImageDto[] imageDtos = imageService.getImagesByMessageId(id);
        messageDto.setImageDtoSet(imageDtos);
        return messageDto;
    }

    @Override
    public MessageDto[] getMessagesByChatId(Long chadId) {
        Message[] messages = messageRepository.getMessagesByChatId(chadId);
        MessageDto[] messageDtos = new MessageDto[messages.length];
        for (int i = 0; i < messages.length; i++){
            MessageDto messageDto = messageDtoConverter.toMessageDto(messages[i]);
            ImageDto[] imageDtos = imageService.getImagesByMessageId(messageDto.getId());
            Set<ImageDto> imageDtoSet = new HashSet<>();

            messageDto.setImageDtoSet(imageDtos);
            messageDtos[i] = messageDto;
        }
        return messageDtos;
    }
}
