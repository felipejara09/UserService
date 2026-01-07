package com.pragma.powerup.domain.exception;

import static com.pragma.powerup.domain.util.ExceptionConstants.EMAIL_ALREADY_EXISTS;

public class EmailAlreadyExistsException extends DomainException {
    public EmailAlreadyExistsException() {
        super(EMAIL_ALREADY_EXISTS);
    }
}
