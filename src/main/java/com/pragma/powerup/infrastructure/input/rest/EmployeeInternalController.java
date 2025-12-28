package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.response.EmployeeRestaurantResponseDto;
import com.pragma.powerup.application.handler.IEmployeeRestaurantHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internal")
@RequiredArgsConstructor
public class EmployeeInternalController {

    private final IEmployeeRestaurantHandler handler;

    @PreAuthorize("hasRole('EMPLOYED')")
    @GetMapping("/employees/me/restaurant")
    public EmployeeRestaurantResponseDto myRestaurant() {
        return handler.getMyRestaurant();
    }
}
