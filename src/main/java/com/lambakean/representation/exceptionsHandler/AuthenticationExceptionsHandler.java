package com.lambakean.representation.exceptionsHandler;

import com.lambakean.representation.dto.ExceptionDto;
import com.lambakean.representation.dto.ResponseWithExceptionsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Set;

@ControllerAdvice
public class AuthenticationExceptionsHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseWithExceptionsDto> handleBadCredentialsException(BadCredentialsException e) {

        Set<ExceptionDto> exceptions = Collections.singleton(new ExceptionDto("badCredentials", e.getMessage()));

        ResponseWithExceptionsDto responseDto = new ResponseWithExceptionsDto(exceptions);

        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

}
