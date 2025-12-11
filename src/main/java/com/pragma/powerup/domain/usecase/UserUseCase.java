package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserService;
import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.util.RoleConstants;
import com.pragma.powerup.domain.util.ValidationConstants;
import com.pragma.powerup.domain.validation.UserBusinessValidator;
import com.pragma.powerup.domain.validation.UserDataValidator;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.regex.Pattern;


@AllArgsConstructor
public class UserUseCase implements IUserService {

    private final IUserPersistencePort persistence;
    private final IPasswordEncoderPort passwordEncoder;

    private final UserDataValidator dataValidator;
    private final UserBusinessValidator businessValidator;

    @Override
    public void createOwner(User user) {

        dataValidator.validate(user);

        businessValidator.validate(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        persistence.save(user);
    }
}

