package com.lambakean.domain.service;

import com.lambakean.representation.dto.MessageDto;
import org.springframework.validation.BindingResult;

public interface MessageService {
    MessageDto create(MessageDto messageDto, BindingResult bindingResult);
    MessageDto delete(Long id);
}
