package com.pragma.powerup.domain.validation;

import com.pragma.powerup.domain.exception.EmailAlreadyExistsException;
import com.pragma.powerup.domain.exception.InvalidRoleException;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.util.RoleConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserBusinessValidatorTest {

    @Mock
    private IUserPersistencePort persistence;

    private UserBusinessValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserBusinessValidator(persistence);
    }

    private User buildUser() {
        User u = new User();
        u.setEmail("test@mail.com");
        u.setRoleId(RoleConstants.ROLE_OWNER);
        return u;
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        User u = buildUser();

        when(persistence.findByEmail(u.getEmail())).thenReturn(new User());

        assertThrows(EmailAlreadyExistsException.class,
                () -> validator.validate(u));
    }

    @Test
    void shouldThrowInvalidRole() {
        User u = buildUser();
        u.setRoleId(999L);

        when(persistence.findByEmail(u.getEmail())).thenReturn(null);

        assertThrows(InvalidRoleException.class,
                () -> validator.validate(u));
    }

    @Test
    void shouldNotThrowWhenValid() {
        User u = buildUser();

        when(persistence.findByEmail(u.getEmail())).thenReturn(null);

        assertDoesNotThrow(() -> validator.validate(u));
    }
}

