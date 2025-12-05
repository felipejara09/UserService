package com.pragma.powerup.domain.exception;

import static com.pragma.powerup.domain.util.ExceptionConstants.INVALID_EMAIL_MESSAGE;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super(INVALID_EMAIL_MESSAGE);
    }
}
