package com.lambakean.representation.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

public class MessageDto {  // todo add validation constraints

    private Long id;
    private Long chatId;
    private Long  userId;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime whenCreated;

    private ImageDto[] imageDtoSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(LocalDateTime whenCreated) {
        this.whenCreated = whenCreated;
    }

    public ImageDto[] getImageDtoSet() {
        return imageDtoSet;
    }

    public void setImageDtoSet(ImageDto[] imageDtoSet) {
        this.imageDtoSet = imageDtoSet;
    }

}