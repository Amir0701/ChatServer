package com.lambakean.representation.dto;

import java.util.Set;

public class ResponseWithExceptionsDto {

    private Set<ExceptionDto> exceptions;

    public ResponseWithExceptionsDto(Set<ExceptionDto> exceptions) {
        this.exceptions = exceptions;
    }

    public ResponseWithExceptionsDto() {}


    public Set<ExceptionDto> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Set<ExceptionDto> exceptions) {
        this.exceptions = exceptions;
    }
}