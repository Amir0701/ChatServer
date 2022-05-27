package com.lambakean.domain.service;

import com.lambakean.representation.dto.MessageDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

public interface MessageService {
    MessageDto create(MessageDto messageDto);
    MessageDto delete(Long id);
    MessageDto getById(Long id);
    MessageDto[] getMessagesByChatId(Long chadId);
    MessageDto add(MultipartFile[] multipartFile, Long chatId, Long userId);
}
