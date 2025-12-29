package com.pragma.powerup.domain.exception;

public class UserPhoneNotFoundException extends DomainException {
    public UserPhoneNotFoundException() {
        super("User phone number was not found");
    }
}
