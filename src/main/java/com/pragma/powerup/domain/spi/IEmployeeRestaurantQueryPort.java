package com.pragma.powerup.domain.spi;

public interface IEmployeeRestaurantQueryPort {
    Long findRestaurantIdByEmployeeId(Long employeeId);
}
