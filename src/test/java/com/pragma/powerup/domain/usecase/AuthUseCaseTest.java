package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.InvalidCredentialsException;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IJwtProviderPort;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.util.RoleConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthUseCaseTest {

    private IUserPersistencePort userPersistencePort;
    private IPasswordEncoderPort passwordEncoderPort;
    private IJwtProviderPort jwtProviderPort;
    private AuthUseCase authUseCase;

    @BeforeEach
    void setUp() {
        userPersistencePort = mock(IUserPersistencePort.class);
        passwordEncoderPort = mock(IPasswordEncoderPort.class);
        jwtProviderPort = mock(IJwtProviderPort.class);

        authUseCase = new AuthUseCase(
                userPersistencePort,
                passwordEncoderPort,
                jwtProviderPort
        );
    }



    @Test
    void shouldLoginSuccessfullyAsAdmin() {

        User user = buildUser(
                1L,
                "admin@test.com",
                "encoded",
                RoleConstants.ROLE_ADMIN
        );

        when(userPersistencePort.findByEmail("admin@test.com"))
                .thenReturn(user);

        when(passwordEncoderPort.matches("password", "encoded"))
                .thenReturn(true);

        when(jwtProviderPort.generateToken(
                1L, "admin@test.com", "ADMIN"
        )).thenReturn("jwt-token");

        String token =
                authUseCase.login("admin@test.com", "password");

        assertEquals("jwt-token", token);
    }

    @Test
    void shouldLoginSuccessfullyAsOwner() {

        User user = buildUser(
                2L,
                "owner@test.com",
                "encoded",
                RoleConstants.ROLE_OWNER
        );

        when(userPersistencePort.findByEmail("owner@test.com"))
                .thenReturn(user);

        when(passwordEncoderPort.matches("password", "encoded"))
                .thenReturn(true);

        when(jwtProviderPort.generateToken(
                2L, "owner@test.com", "OWNER"
        )).thenReturn("jwt-token");

        String token =
                authUseCase.login("owner@test.com", "password");

        assertNotNull(token);
    }

    @Test
    void shouldLoginSuccessfullyAsEmployee() {

        User user = buildUser(
                3L,
                "employee@test.com",
                "encoded",
                RoleConstants.ROLE_EMPLOYEE
        );

        when(userPersistencePort.findByEmail("employee@test.com"))
                .thenReturn(user);

        when(passwordEncoderPort.matches("password", "encoded"))
                .thenReturn(true);

        when(jwtProviderPort.generateToken(
                3L, "employee@test.com", "EMPLOYED"
        )).thenReturn("jwt-token");

        String token =
                authUseCase.login("employee@test.com", "password");

        assertNotNull(token);
    }

    @Test
    void shouldLoginSuccessfullyAsClient() {

        User user = buildUser(
                4L,
                "client@test.com",
                "encoded",
                RoleConstants.ROLE_CLIENT
        );

        when(userPersistencePort.findByEmail("client@test.com"))
                .thenReturn(user);

        when(passwordEncoderPort.matches("password", "encoded"))
                .thenReturn(true);

        when(jwtProviderPort.generateToken(
                4L, "client@test.com", "CLIENT"
        )).thenReturn("jwt-token");

        String token =
                authUseCase.login("client@test.com", "password");

        assertNotNull(token);
    }


    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {

        when(userPersistencePort.findByEmail("nope@test.com"))
                .thenReturn(null);

        assertThrows(
                InvalidCredentialsException.class,
                () -> authUseCase.login("nope@test.com", "password")
        );

        verifyNoInteractions(passwordEncoderPort);
        verifyNoInteractions(jwtProviderPort);
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {

        User user = buildUser(
                1L,
                "user@test.com",
                "encoded",
                RoleConstants.ROLE_CLIENT
        );

        when(userPersistencePort.findByEmail("user@test.com"))
                .thenReturn(user);

        when(passwordEncoderPort.matches("wrong", "encoded"))
                .thenReturn(false);

        assertThrows(
                InvalidCredentialsException.class,
                () -> authUseCase.login("user@test.com", "wrong")
        );

        verify(jwtProviderPort, never()).generateToken(any(), any(), any());
    }

    @Test
    void shouldThrowExceptionWhenRoleIsUnknown() {

        User user = buildUser(
                1L,
                "user@test.com",
                "encoded",
                999L
        );

        when(userPersistencePort.findByEmail("user@test.com"))
                .thenReturn(user);

        when(passwordEncoderPort.matches("password", "encoded"))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> authUseCase.login("user@test.com", "password")
        );
    }

    private User buildUser(
            Long id,
            String email,
            String password,
            Long roleId
    ) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoleId(roleId);
        return user;
    }
}
