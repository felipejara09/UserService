package com.pragma.powerup.domain.exception;

import static com.pragma.powerup.domain.util.ExceptionConstants.INVALID_ROLE_MESSAGE;

public class InvalidRoleException extends DomainException {
    public InvalidRoleException() {
        super(INVALID_ROLE_MESSAGE);
    }
}
