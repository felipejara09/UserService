package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.response.EmployeeRestaurantResponseDto;
import com.pragma.powerup.application.handler.IEmployeeRestaurantHandler;
import com.pragma.powerup.domain.api.IEmployeeRestaurantService;
import com.pragma.powerup.infrastructure.segurity.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeRestaurantHandler implements IEmployeeRestaurantHandler {

    private final IEmployeeRestaurantService employeeRestaurantService;

    @Override
    public EmployeeRestaurantResponseDto getMyRestaurant() {
        Long employeeId = SecurityUtils.getUserId();
        Long restaurantId = employeeRestaurantService.getMyRestaurantId(employeeId);
        return new EmployeeRestaurantResponseDto(restaurantId);
    }
}
