package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.spi.IEmployeeRestaurantQueryPort;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeRestaurantJpaAdapter implements IEmployeeRestaurantQueryPort {

    private final IUserRepository userRepository;

    @Override
    public Long findRestaurantIdByEmployeeId(Long employeeId) {
        return userRepository.findRestaurantIdById(employeeId).orElse(null);
    }
}
