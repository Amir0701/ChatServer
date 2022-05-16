package com.lambakean.domain.service;

import com.lambakean.data.model.Image;
import com.lambakean.data.repository.ImageRepository;
import com.lambakean.representation.dto.ImageDto;
import com.lambakean.representation.dtoConverter.ImageDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageServiceImpl implements ImageService{
    private ImageRepository imageRepository;
    private ImageDtoConverter imageDtoConverter;
    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, ImageDtoConverter imageDtoConverter){
        this.imageRepository = imageRepository;
        this.imageDtoConverter = imageDtoConverter;
    }

    @Override
    public ImageDto create(ImageDto imageDto) {
        Image image = imageDtoConverter.toImage(imageDto);
        imageRepository.save(image);
        return imageDto;
    }

    @Override
    public ImageDto getById(Long id) {
        Image image = imageRepository.getById(id);
        ImageDto imageDto = imageDtoConverter.toImageDto(image);
        return imageDto;
    }

    @Override
    public ImageDto delete(Long id) {
        Image image = imageRepository.getById(id);
        imageRepository.deleteById(id);
        ImageDto imageDto = imageDtoConverter.toImageDto(image);
        return imageDto;
    }

    @Override
    public ImageDto[] getImagesByMessageId(Long messageId) {
        Image[] images = imageRepository.getImagesByMessageId(messageId);
        ImageDto[] imageDtos = new ImageDto[images.length];
        for (int i = 0; i < images.length; i++){
            imageDtos[i] = imageDtoConverter.toImageDto(images[i]);
        }
        return imageDtos;
    }
}
