package com.lambakean.representation.dtoConverter;

import com.lambakean.data.model.Chat;
import com.lambakean.representation.dto.ChatDto;

public interface ChatDtoConverter {

    Chat toChat(ChatDto chatDto);

    ChatDto toChatDto(Chat chat);
}
