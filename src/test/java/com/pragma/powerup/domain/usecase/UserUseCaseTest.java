package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.EmailAlreadyExistsException;
import com.pragma.powerup.domain.exception.InvalidEmailException;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.util.RoleConstants;
import com.pragma.powerup.domain.validation.UserBusinessValidator;
import com.pragma.powerup.domain.validation.UserDataValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort persistence;

    @Mock
    private IPasswordEncoderPort passwordEncoder;

    @Mock
    private UserDataValidator dataValidator;

    @Mock
    private UserBusinessValidator businessValidator;

    @InjectMocks
    private UserUseCase useCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("123");
        user.setRoleId(RoleConstants.ROLE_OWNER);
    }

    @Test
    void shouldCreateOwnerSuccessfully() {

        when(passwordEncoder.encode("123")).thenReturn("encoded123");

        useCase.createOwner(user);

        verify(dataValidator).validate(user);
        verify(businessValidator).validate(user);
        verify(passwordEncoder).encode("123");
        verify(persistence).save(user);

        assertEquals("encoded123", user.getPassword());
    }

    @Test
    void shouldThrowWhenDataValidatorFails() {
        doThrow(new InvalidEmailException()).when(dataValidator).validate(user);

        assertThrows(InvalidEmailException.class, () -> useCase.createOwner(user));

        verify(businessValidator, never()).validate(any());
        verify(persistence, never()).save(any());
    }

    @Test
    void shouldThrowWhenBusinessValidatorFails() {
        doThrow(new EmailAlreadyExistsException()).when(businessValidator).validate(user);

        assertThrows(EmailAlreadyExistsException.class, () -> useCase.createOwner(user));

        verify(persistence, never()).save(any());
    }
}