package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.User;
import java.util.List;

public interface IUserPersistencePort {
    void save(User user);//
    User findByEmail(String email);
    User findById(Long id);
}