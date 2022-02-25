package com.lambakean.representation.dtoConverter;

import com.lambakean.data.model.Message;
import com.lambakean.representation.dto.MessageDto;

public interface MessageDtoConverter {

    Message toMessage(MessageDto messageDto);

    MessageDto toMessageDto(Message message);
}
