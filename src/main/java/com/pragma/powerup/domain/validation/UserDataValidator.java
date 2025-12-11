package com.pragma.powerup.domain.validation;


import com.pragma.powerup.domain.exception.InvalidDocumentException;
import com.pragma.powerup.domain.exception.InvalidEmailException;
import com.pragma.powerup.domain.exception.InvalidPhoneException;
import com.pragma.powerup.domain.exception.UnderAgeException;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.util.ValidationConstants;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class UserDataValidator {

    public void validate(User user) {

        if (!Pattern.matches(ValidationConstants.EMAIL_REGEX, user.getEmail())) {
            throw new InvalidEmailException();
        }

        if (!Pattern.matches(ValidationConstants.PHONE_REGEX, user.getPhoneNumber())) {
            throw new InvalidPhoneException();
        }

        if (!Pattern.matches(ValidationConstants.DOCUMENT_REGEX, user.getDocumentId())) {
            throw new InvalidDocumentException();
        }

        if (user.getBirthDate() == null) {
            throw new UnderAgeException();
        }

        LocalDate limitDate = LocalDate.now().minusYears(ValidationConstants.MIN_AGE);
        if (user.getBirthDate().isAfter(limitDate)) {
            throw new UnderAgeException();
        }
    }
}