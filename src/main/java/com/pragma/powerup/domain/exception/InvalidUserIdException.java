package com.pragma.powerup.domain.exception;

public class InvalidUserIdException extends DomainException {
    public InvalidUserIdException() {
        super("Invalid user id");
    }
}
