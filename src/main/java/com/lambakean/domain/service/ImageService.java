package com.lambakean.domain.service;

import com.lambakean.representation.dto.ImageDto;

public interface ImageService {
    ImageDto create(ImageDto imageDto);
    ImageDto getById(Long id);
    ImageDto delete(Long id);
}
