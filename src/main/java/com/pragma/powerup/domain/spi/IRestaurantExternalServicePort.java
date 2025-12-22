package com.pragma.powerup.domain.spi;

public interface IRestaurantExternalServicePort {
    boolean isRestaurantOwnedBy(Long restaurantId, String token);
}

