package com.pragma.powerup.infrastructure.exceptionhandler;

import com.pragma.powerup.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler({
            InvalidEmailException.class,
            EmailAlreadyExistsException.class,
            InvalidPhoneException.class,
            InvalidDocumentException.class,
            UnderAgeException.class,
            InvalidRoleException.class
    })
    public ResponseEntity<ExceptionResponse> handleBadRequest(RuntimeException ex) {
        ExceptionResponse response = new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        ExceptionResponse response = new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.toString()
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
