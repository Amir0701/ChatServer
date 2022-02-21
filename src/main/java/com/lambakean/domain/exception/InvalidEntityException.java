package com.lambakean.domain.exception;

import java.util.Set;

public class InvalidEntityException extends RuntimeException {

    private final Set<String> messages;

    public InvalidEntityException(Set<String> messages) {
        this.messages = messages;
    }


    public Set<String> getMessages() {
        return messages;
    }
}