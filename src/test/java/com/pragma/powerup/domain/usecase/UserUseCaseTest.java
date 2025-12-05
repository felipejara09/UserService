package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.util.RoleConstants;
import com.pragma.powerup.domain.util.ValidationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private UserUseCase userUseCase;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = createValidUser();
    }

    private User createValidUser() {
        User user = new User();
        user.setName("Felipe");
        user.setLastName("Jaramillo");
        user.setDocumentId("123456789");
        user.setPhoneNumber("+573001234567");
        user.setEmail("felipe@test.com");
        user.setPassword("MiClaveSegura");
        user.setRoleId(RoleConstants.ROLE_OWNER);
        user.setBirthDate(LocalDate.now().minusYears(25));
        return user;
    }

    @Test
    void createOwner_success() {
        User user = createValidUser();

        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(null);
        when(passwordEncoderPort.encode("MiClaveSegura")).thenReturn("encoded-password");

        userUseCase.createOwner(user);


        assertEquals("encoded-password", user.getPassword());
        assertEquals(RoleConstants.ROLE_OWNER, user.getRoleId());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userPersistencePort).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals("encoded-password", savedUser.getPassword());

        verify(passwordEncoderPort, times(1)).encode("MiClaveSegura");
    }

    @Test
    void createOwner_invalidEmail_throwsInvalidEmailException() {
        // Arrange
        validUser.setEmail("correo-malo"); // no cumple regex

        // Act - Assert
        assertThrows(InvalidEmailException.class,
                () -> userUseCase.createOwner(validUser));

        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void createOwner_duplicatedEmail_throwsEmailAlreadyExistsException() {
        // Arrange
        when(userPersistencePort.findByEmail(validUser.getEmail()))
                .thenReturn(new User());

        // Act - Assert
        assertThrows(EmailAlreadyExistsException.class,
                () -> userUseCase.createOwner(validUser));

        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void createOwner_invalidPhone_throwsInvalidPhoneException() {
        // Arrange
        when(userPersistencePort.findByEmail(validUser.getEmail())).thenReturn(null);
        validUser.setPhoneNumber("123"); // no cumple regex

        // Act - Assert
        assertThrows(InvalidPhoneException.class,
                () -> userUseCase.createOwner(validUser));

        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void createOwner_invalidDocument_throwsInvalidDocumentException() {
        // Arrange
        when(userPersistencePort.findByEmail(validUser.getEmail())).thenReturn(null);
        validUser.setDocumentId("ABC123"); // no cumple regex

        // Act - Assert
        assertThrows(InvalidDocumentException.class,
                () -> userUseCase.createOwner(validUser));

        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void createOwner_underAge_throwsUnderAgeException() {
        // Arrange
        when(userPersistencePort.findByEmail(validUser.getEmail())).thenReturn(null);
        validUser.setBirthDate(LocalDate.now()
                .minusYears(ValidationConstants.MIN_AGE - 1)); // menor

        // Act - Assert
        assertThrows(UnderAgeException.class,
                () -> userUseCase.createOwner(validUser));

        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void createOwner_nullRole_throwsInvalidRoleException() {
        // Arrange
        when(userPersistencePort.findByEmail(validUser.getEmail())).thenReturn(null);
        validUser.setRoleId(null);

        // Act - Assert
        assertThrows(InvalidRoleException.class,
                () -> userUseCase.createOwner(validUser));

        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void createOwner_notValidRole_throwsInvalidRoleException() {
        // Arrange
        when(userPersistencePort.findByEmail(validUser.getEmail())).thenReturn(null);
        validUser.setRoleId(999L); // no está en VALID_ROLES

        // Act - Assert
        assertThrows(InvalidRoleException.class,
                () -> userUseCase.createOwner(validUser));

        verify(userPersistencePort, never()).save(any());
    }
}
