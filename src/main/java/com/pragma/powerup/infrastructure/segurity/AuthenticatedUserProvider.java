package com.pragma.powerup.infrastructure.segurity;

public interface AuthenticatedUserProvider {
    Long getUserId();
    String getEmail();
    String getRole();
    String getToken();
}
