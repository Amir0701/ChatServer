package com.lambakean.representation.exceptionsHandler;

import com.lambakean.domain.exception.UserNotLoggedInException;
import com.lambakean.representation.dto.ExceptionDto;
import com.lambakean.representation.dto.ResponseWithExceptionsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Set;

@ControllerAdvice
public class UserRelatedExceptionsHandler {

    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<ResponseWithExceptionsDto> handleUserNotLoggedInException(UserNotLoggedInException e){
        Set<ExceptionDto> exceptions = Collections.singleton(new ExceptionDto(
                "User not logged in",
                e.getMessage()
        ));

        ResponseWithExceptionsDto responseDto = new ResponseWithExceptionsDto(exceptions);
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }
}
