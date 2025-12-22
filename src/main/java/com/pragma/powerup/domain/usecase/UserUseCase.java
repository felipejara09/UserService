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

        user.setRoleId(RoleConstants.ROLE_OWNER);
        dataValidator.validate(user);

        businessValidator.validate(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        persistence.save(user);
    }

    @Override
    public void createEmployed(User employed, Long restaurantId, String token) {

        if (!restaurantExternalServicePort.isRestaurantOwnedBy(restaurantId, token)) {
            throw new ForbiddenRestaurantAccessException();
        }

        employed.setRestaurantId(restaurantId);


        employed.setRoleId(RoleConstants.ROLE_EMPLOYEE);

        if (RoleConstants.ROLE_EMPLOYEE.equals(employed.getRoleId()) &&
                employed.getRestaurantId() == null) {
                throw new IllegalArgumentException("Employee must have a restaurantId");
            }

        dataValidator.validate(employed);
        businessValidator.validate(employed);

        employed.setPassword(passwordEncoder.encode(employed.getPassword()));
        persistence.save(employed);
    }

    @Override
    public void registerClient(User user) {

        user.setRoleId(RoleConstants.ROLE_CLIENT);
        dataValidator.validate(user);
        businessValidator.validate(user);

        user.setPassword(passwordEncoder.encode(user.getPassword())); // bcrypt

        persistence.save(user);
    }

}

