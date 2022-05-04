package com.lambakean.representation.dtoConverter;

import com.lambakean.data.model.Image;
import com.lambakean.representation.dto.ImageDto;

public interface ImageDtoConverter {
    Image toImage(ImageDto imageDto);
    ImageDto toImageDto(Image image);
}
