package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.User;

public interface IUserService {
    void createOwner(User user);
    void createEmployed(User employed, Long restaurantId, String ownerId);
}
