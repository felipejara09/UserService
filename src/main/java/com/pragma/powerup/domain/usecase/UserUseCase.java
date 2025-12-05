package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserService;
import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.util.RoleConstants;
import com.pragma.powerup.domain.util.ValidationConstants;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.regex.Pattern;


@AllArgsConstructor
public class UserUseCase implements IUserService {

    private final IUserPersistencePort persistence;
    private final IPasswordEncoderPort passwordEncoder;


    @Override
    public void createOwner(User user) {
        if (!Pattern.matches(ValidationConstants.EMAIL_REGEX, user.getEmail())) {
            throw new InvalidEmailException();
        }
        if (persistence.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException();
        }
        if (!Pattern.matches(ValidationConstants.PHONE_REGEX, user.getPhoneNumber())) {
            throw new InvalidPhoneException();
        }
        if (!Pattern.matches(ValidationConstants.DOCUMENT_REGEX, user.getDocumentId())) {
            throw new InvalidDocumentException();
        }
        LocalDate limitDate = LocalDate.now().minusYears(ValidationConstants.MIN_AGE);
        if (user.getBirthDate().isAfter(limitDate)) {
            throw new UnderAgeException();
        }
        if (user.getRoleId() == null ||
                !RoleConstants.VALID_ROLES.contains(user.getRoleId())) {
            throw new InvalidRoleException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        persistence.save(user);
    }

}