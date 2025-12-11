package com.pragma.powerup.domain.validation;

import com.pragma.powerup.domain.exception.EmailAlreadyExistsException;
import com.pragma.powerup.domain.exception.InvalidRoleException;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.util.RoleConstants;

public class UserBusinessValidator {
    private final IUserPersistencePort persistence;

    public UserBusinessValidator(IUserPersistencePort persistence) {
        this.persistence = persistence;
    }

    public void validate(User user) {

        if (persistence.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException();
        }

        if (user.getRoleId() == null ||
                !RoleConstants.VALID_ROLES.contains(user.getRoleId())) {
            throw new InvalidRoleException();
        }
    }
}
