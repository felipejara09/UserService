package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRestaurantExternalServicePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.util.RoleConstants;
import com.pragma.powerup.domain.validation.UserBusinessValidator;
import com.pragma.powerup.domain.validation.UserDataValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    private IUserPersistencePort persistence;
    private IPasswordEncoderPort passwordEncoder;
    private IRestaurantExternalServicePort restaurantService;
    private UserDataValidator dataValidator;
    private UserBusinessValidator businessValidator;

    private UserUseCase useCase;

    @BeforeEach
    void setUp() {
        persistence = mock(IUserPersistencePort.class);
        passwordEncoder = mock(IPasswordEncoderPort.class);
        restaurantService = mock(IRestaurantExternalServicePort.class);
        dataValidator = mock(UserDataValidator.class);
        businessValidator = mock(UserBusinessValidator.class);

        useCase = new UserUseCase(
                persistence,
                passwordEncoder,
                restaurantService,
                dataValidator,
                businessValidator
        );
    }


    @Test
    void shouldCreateOwnerSuccessfully() {

        User user = buildUser();

        when(passwordEncoder.encode("password"))
                .thenReturn("encoded");

        useCase.createOwner(user);

        assertEquals(RoleConstants.ROLE_OWNER, user.getRoleId());
        assertEquals("encoded", user.getPassword());

        verify(dataValidator).validate(user);
        verify(businessValidator).validate(user);
        verify(persistence).save(user);
    }

    @Test
    void shouldThrowExceptionWhenOwnerDataIsInvalid() {

        User user = buildUser();

        doThrow(new IllegalArgumentException())
                .when(dataValidator).validate(user);

        assertThrows(
                IllegalArgumentException.class,
                () -> useCase.createOwner(user)
        );

        verify(persistence, never()).save(any());
    }


    @Test
    void shouldCreateEmployeeSuccessfully() {

        User user = buildUser();

        when(restaurantService.isRestaurantOwnedBy(1L, "token"))
                .thenReturn(true);

        when(passwordEncoder.encode("password"))
                .thenReturn("encoded");

        useCase.createEmployed(user, 1L, "token");

        assertEquals(RoleConstants.ROLE_EMPLOYEE, user.getRoleId());
        assertEquals(1L, user.getRestaurantId());

        verify(persistence).save(user);
    }

    @Test
    void shouldThrowForbiddenWhenRestaurantNotOwned() {

        User user = buildUser();

        when(restaurantService.isRestaurantOwnedBy(1L, "token"))
                .thenReturn(false);

        assertThrows(
                ForbiddenRestaurantAccessException.class,
                () -> useCase.createEmployed(user, 1L, "token")
        );

        verify(persistence, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenEmployeeHasNoRestaurant() {

        User user = buildUser();
        
        when(restaurantService.isRestaurantOwnedBy(null, "token"))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> useCase.createEmployed(user, null, "token")
        );

        verify(persistence, never()).save(any());
    }




    @Test
    void shouldRegisterClientSuccessfully() {

        User user = buildUser();

        when(passwordEncoder.encode("password"))
                .thenReturn("encoded");

        useCase.registerClient(user);

        assertEquals(RoleConstants.ROLE_CLIENT, user.getRoleId());
        assertEquals("encoded", user.getPassword());

        verify(persistence).save(user);
    }

    @Test
    void shouldReturnUserPhoneNumberSuccessfully() {

        User user = buildUser();
        user.setPhoneNumber("3001234567");

        when(persistence.findById(1L))
                .thenReturn(user);

        String phone =
                useCase.getUserPhoneNumber(1L);

        assertEquals("3001234567", phone);
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsInvalid() {

        assertThrows(
                InvalidUserIdException.class,
                () -> useCase.getUserPhoneNumber(0L)
        );
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(persistence.findById(1L))
                .thenReturn(null);

        assertThrows(
                UserNotFoundException.class,
                () -> useCase.getUserPhoneNumber(1L)
        );
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsMissing() {

        User user = buildUser();
        user.setPhoneNumber("");

        when(persistence.findById(1L))
                .thenReturn(user);

        assertThrows(
                UserPhoneNotFoundException.class,
                () -> useCase.getUserPhoneNumber(1L)
        );
    }


    private User buildUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setPassword("password");
        return user;
    }
}
