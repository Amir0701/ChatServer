package com.lambakean.representation.exceptionsHandler;

import com.lambakean.data.model.Message;
import com.lambakean.domain.exception.EntityAlreadyExistsException;
import com.lambakean.domain.exception.InvalidEntityException;
import com.lambakean.representation.dto.ExceptionDto;
import com.lambakean.representation.dto.ResponseWithExceptionsDto;
import org.hibernate.mapping.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Set;
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

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ResponseWithExceptionsDto> handleEntityAlreadyExistsException(
            EntityAlreadyExistsException e
    ) {

        Set<ExceptionDto> exceptions = Collections.singleton(new ExceptionDto(
                "entityAlreadyExists",
                e.getMessage()
        ));

        ResponseWithExceptionsDto responseDto = new ResponseWithExceptionsDto(exceptions);

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
