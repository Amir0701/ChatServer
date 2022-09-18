package com.lambakean.domain.service;

import com.lambakean.data.model.Chat;
import com.lambakean.data.model.Image;
import com.lambakean.data.model.Message;
import com.lambakean.data.model.User;
import com.lambakean.data.repository.ImageRepository;
import com.lambakean.data.repository.MessageRepository;
import com.lambakean.domain.exception.UserNotLoggedInException;
import com.lambakean.representation.dto.ChatDto;
import com.lambakean.representation.dto.ImageDto;
import com.lambakean.representation.dto.MessageDto;
import com.lambakean.representation.dto.UserDto;
import com.lambakean.representation.dtoConverter.ChatDtoConverter;
import com.lambakean.representation.dtoConverter.ImageDtoConverter;
import com.lambakean.representation.dtoConverter.MessageDtoConverter;
import com.lambakean.representation.dtoConverter.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MessageServiceImpl implements MessageService {
    private MessageDtoConverter messageDtoConverter;
    private MessageRepository messageRepository;
    private UserService userService;
    private ImageService imageService;
    private UserDtoConverter userDtoConverter;
    private ChatService chatService;
    private ChatDtoConverter chatDtoConverter;
    private ImageRepository imageRepository;
    private ImageDtoConverter imageDtoConverter;

    private static String path = "/opt/chatImages/";

    @Autowired
    public MessageServiceImpl(MessageDtoConverter messageDtoConverter,
                              MessageRepository messageRepository,
                              UserService userService,
                              ImageService imageService,
                              UserDtoConverter userDtoConverter,
                              ChatService chatService,
                              ChatDtoConverter chatDtoConverter,
                              ImageRepository imageRepository,
                              ImageDtoConverter imageDtoConverter) {
        this.messageDtoConverter = messageDtoConverter;
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.imageService = imageService;
        this.userDtoConverter = userDtoConverter;
        this.chatDtoConverter = chatDtoConverter;
        this.chatService = chatService;
        this.imageRepository = imageRepository;
        this.imageDtoConverter = imageDtoConverter;
    }


    @Override
    public MessageDto create(MessageDto messageDto) {
        User user = userService.getCurrentUser();

        if (user == null) {
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

            messageDto.setImageDtoSet(imageDtos);
            messageDtos[i] = messageDto;
        }
        return messageDtos;
    }

    @Override
    public MessageDto add(MultipartFile[] multipartFile, Long chatId, Long userId) {
        Path root = Paths.get(path + userId + "/");
        List<Image> images = new ArrayList<>();

        for (int i = 0; i < multipartFile.length; i++) {
            try {
                File file = new File(root.resolve(multipartFile[i].getOriginalFilename()).toUri());
                if (!file.exists()) file.mkdirs();
                multipartFile[i].transferTo(file.getAbsoluteFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Image newImage = new Image();
            newImage.setUrl(path + userId + "/" + multipartFile[i].getOriginalFilename());
            images.add(newImage);
        }

        UserDto userDto = userService.getUser(userId);
        User user = userDtoConverter.toUser(userDto);
        ChatDto chatDto = chatService.get(chatId);
        Chat chat = chatDtoConverter.toChat(chatDto);

        Message newMessage = new Message();
        newMessage.setUser(user);
        newMessage.setWhenCreated(LocalDateTime.now());
        newMessage.setChat(chat);
        messageRepository.save(newMessage);

        for (Image image : images) {
            image.setMessage(newMessage);
            imageRepository.save(image);
        }

        MessageDto messageDto = messageDtoConverter.toMessageDto(newMessage);

        ImageDto[] imageDtos = images.stream()
                .map(image -> imageDtoConverter.toImageDto(image)).toArray(ImageDto[]::new);
        messageDto.setImageDtoSet(imageDtos);
        return messageDto;
    }
}
