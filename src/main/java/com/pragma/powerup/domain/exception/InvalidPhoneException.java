package com.pragma.powerup.domain.exception;

import static com.pragma.powerup.domain.util.ExceptionConstants.INVALID_PHONE_MESSAGE;

public class InvalidPhoneException extends DomainException {
    public InvalidPhoneException() {
        super(INVALID_PHONE_MESSAGE);
    }
}
