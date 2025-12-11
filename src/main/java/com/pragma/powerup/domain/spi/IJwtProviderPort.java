package com.pragma.powerup.domain.spi;

public interface IJwtProviderPort {
    String generateToken(Long userId, String email, String role);
}
