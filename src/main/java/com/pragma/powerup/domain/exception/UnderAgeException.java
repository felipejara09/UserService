package com.pragma.powerup.domain.exception;

import static com.pragma.powerup.domain.util.ExceptionConstants.UNDER_AGE_MESSAGE;

public class UnderAgeException extends RuntimeException {
    public UnderAgeException() {
        super(UNDER_AGE_MESSAGE);
    }
}
