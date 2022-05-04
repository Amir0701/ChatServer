package com.lambakean.representation.dtoConverter.impl;

import com.lambakean.data.model.Image;
import com.lambakean.data.model.Message;
import com.lambakean.representation.dto.ImageDto;
import com.lambakean.representation.dtoConverter.ImageDtoConverter;
import org.springframework.stereotype.Component;

@Component
public class ImageDtoConverterImpl implements ImageDtoConverter {

    @Override
    public Image toImage(ImageDto imageDto) {
        Image image = new Image();
        image.setId(imageDto.getId());
        image.setMessage(new Message(imageDto.getMessageId()));
        return null;
    }

    @Override
    public ImageDto toImageDto(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(image.getId());
        imageDto.setMessageId(image.getMessage().getId());
        imageDto.setUrl(image.getUrl());
        return imageDto;
    }
}
