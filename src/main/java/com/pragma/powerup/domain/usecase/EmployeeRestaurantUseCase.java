package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IEmployeeRestaurantService;
import com.pragma.powerup.domain.exception.EmployeeNotAssignedToRestaurantException;
import com.pragma.powerup.domain.spi.IEmployeeRestaurantQueryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeRestaurantUseCase implements IEmployeeRestaurantService {

    private final IEmployeeRestaurantQueryPort employeeRestaurantQueryPort;

    @Override
    public Long getMyRestaurantId(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employeeId");
        }

        Long restaurantId = employeeRestaurantQueryPort.findRestaurantIdByEmployeeId(employeeId);

        if (restaurantId == null || restaurantId <= 0) {
            throw new EmployeeNotAssignedToRestaurantException();
        }

        return restaurantId;
    }
}
