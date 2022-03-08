package com.lambakean.domain.service;

import com.lambakean.representation.dto.ChatDto;
import org.springframework.validation.BindingResult;

public interface ChatService {

    ChatDto create(ChatDto chatData, BindingResult bindingResult);

}
