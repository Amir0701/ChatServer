package com.lambakean.representation.exceptionsHandler;

import com.lambakean.domain.exception.InvalidEntityException;
import com.lambakean.representation.dto.ExceptionDto;
import com.lambakean.representation.dto.ResponseWithExceptionsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class EntityRelatedExceptionsHandler {

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ResponseWithExceptionsDto> handleInvalidEntityException(InvalidEntityException e) {

        ResponseWithExceptionsDto responseDto = new ResponseWithExceptionsDto(
            e.getMessages().stream()
                    .map(message -> new ExceptionDto("invalidEntity", message))
                    .collect(Collectors.toSet())
        );

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
