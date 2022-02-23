package com.lambakean.representation.dto;

import com.lambakean.data.model.Message;
import com.lambakean.data.model.Subscription;

import java.time.LocalDateTime;
import java.util.Set;

public class ChatDto {
    private Long id;

    private String name;


    private LocalDateTime whenCreated;


    private Set<Subscription> subscriptions;


    private Set<Message> messages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getWhenCreated() {
        return whenCreated;
    }


    public Long getId() {
        return id;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public Set<Message> getMessages() {
        return messages;
    }
}
