package com.pragma.powerup.domain.validation;

import com.pragma.powerup.domain.exception.InvalidDocumentException;
import com.pragma.powerup.domain.exception.InvalidEmailException;
import com.pragma.powerup.domain.exception.InvalidPhoneException;
import com.pragma.powerup.domain.exception.UnderAgeException;
import com.pragma.powerup.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserDataValidatorTest {
    private UserDataValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserDataValidator();
    }

    private User buildValidUser() {
        User u = new User();
        u.setEmail("test@mail.com");
        u.setPhoneNumber("+573001234567");
        u.setDocumentId("12345678");
        u.setBirthDate(LocalDate.now().minusYears(20));
        return u;
    }

    @Test
    void shouldThrowInvalidEmail() {
        User u = buildValidUser();
        u.setEmail("invalid");

        assertThrows(InvalidEmailException.class, () -> validator.validate(u));
    }

    @Test
    void shouldThrowInvalidPhone() {
        User u = buildValidUser();
        u.setPhoneNumber("12345");

        assertThrows(InvalidPhoneException.class, () -> validator.validate(u));
    }

    @Test
    void shouldThrowInvalidDocument() {
        User u = buildValidUser();
        u.setDocumentId("ABC123");

        assertThrows(InvalidDocumentException.class, () -> validator.validate(u));
    }

    @Test
    void shouldThrowUnderAge() {
        User u = buildValidUser();
        u.setBirthDate(LocalDate.now().minusYears(10));

        assertThrows(UnderAgeException.class, () -> validator.validate(u));
    }

    @Test
    void shouldNotThrowWhenValid() {
        User u = buildValidUser();
        assertDoesNotThrow(() -> validator.validate(u));
    }
}
