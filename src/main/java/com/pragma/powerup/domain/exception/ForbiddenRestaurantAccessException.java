package com.pragma.powerup.domain.exception;

public class ForbiddenRestaurantAccessException extends DomainException {
    public ForbiddenRestaurantAccessException() {
        super("You are not allowed to manage employees for this restaurant.");
    }
}
