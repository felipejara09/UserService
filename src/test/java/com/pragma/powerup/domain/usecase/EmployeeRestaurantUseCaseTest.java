package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.EmployeeNotAssignedToRestaurantException;
import com.pragma.powerup.domain.spi.IEmployeeRestaurantQueryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeRestaurantUseCaseTest {

    private IEmployeeRestaurantQueryPort queryPort;
    private EmployeeRestaurantUseCase useCase;

    @BeforeEach
    void setUp() {
        queryPort = mock(IEmployeeRestaurantQueryPort.class);
        useCase = new EmployeeRestaurantUseCase(queryPort);
    }


    @Test
    void shouldReturnRestaurantIdWhenEmployeeIsAssigned() {

        when(queryPort.findRestaurantIdByEmployeeId(10L))
                .thenReturn(5L);

        Long restaurantId = useCase.getMyRestaurantId(10L);

        assertEquals(5L, restaurantId);
        verify(queryPort).findRestaurantIdByEmployeeId(10L);
    }



    @Test
    void shouldThrowExceptionWhenEmployeeIdIsNull() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.getMyRestaurantId(null)
        );

        assertEquals("Invalid employeeId", exception.getMessage());
        verifyNoInteractions(queryPort);
    }

    @Test
    void shouldThrowExceptionWhenEmployeeIdIsZero() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.getMyRestaurantId(0L)
        );

        assertEquals("Invalid employeeId", exception.getMessage());
        verifyNoInteractions(queryPort);
    }

    @Test
    void shouldThrowExceptionWhenEmployeeIdIsNegative() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.getMyRestaurantId(-5L)
        );

        assertEquals("Invalid employeeId", exception.getMessage());
        verifyNoInteractions(queryPort);
    }


    @Test
    void shouldThrowExceptionWhenEmployeeIsNotAssigned() {

        when(queryPort.findRestaurantIdByEmployeeId(10L))
                .thenReturn(null);

        assertThrows(
                EmployeeNotAssignedToRestaurantException.class,
                () -> useCase.getMyRestaurantId(10L)
        );
    }

    @Test
    void shouldThrowExceptionWhenRestaurantIdIsInvalid() {

        when(queryPort.findRestaurantIdByEmployeeId(10L))
                .thenReturn(0L);

        assertThrows(
                EmployeeNotAssignedToRestaurantException.class,
                () -> useCase.getMyRestaurantId(10L)
        );
    }
}

