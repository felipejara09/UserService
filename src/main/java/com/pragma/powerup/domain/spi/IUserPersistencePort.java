package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.User;


public interface IUserPersistencePort {
    void save(User user);//
    User findByEmail(String email);
    User findById(Long id);
}