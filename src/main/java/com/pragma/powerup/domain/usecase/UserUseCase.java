package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserService;
import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRestaurantExternalServicePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.util.RoleConstants;
import com.pragma.powerup.domain.validation.UserBusinessValidator;
import com.pragma.powerup.domain.validation.UserDataValidator;
import lombok.AllArgsConstructor;




@AllArgsConstructor
public class UserUseCase implements IUserService {

    private final IUserPersistencePort persistence;
    private final IPasswordEncoderPort passwordEncoder;
    private final IRestaurantExternalServicePort restaurantExternalServicePort;

    private final UserDataValidator dataValidator;
    private final UserBusinessValidator businessValidator;

    @Override
    public void createOwner(User user) {

        dataValidator.validate(user);

        businessValidator.validate(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        persistence.save(user);
    }

    @Override
    public void createEmployed(User employed, Long restaurantId, String ownerId) {

        if (!restaurantExternalServicePort.isRestaurantOwnedBy(restaurantId, ownerId)) {
            throw new ForbiddenRestaurantAccessException();
        }
        employed.setRoleId(RoleConstants.ROLE_EMPLOYEE);
        dataValidator.validate(employed);
        businessValidator.validate(employed);

        employed.setRoleId(RoleConstants.ROLE_EMPLOYEE);
        employed.setPassword(passwordEncoder.encode(employed.getPassword()));
        persistence.save(employed);
    }

}

