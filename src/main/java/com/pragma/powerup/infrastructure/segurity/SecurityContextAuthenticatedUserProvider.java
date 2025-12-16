package com.pragma.powerup.infrastructure.segurity;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextAuthenticatedUserProvider implements AuthenticatedUserProvider {

    @Override
    public Long getUserId() {
        return principal().getUserId();
    }

    @Override
    public String getEmail() {
        return principal().getEmail();
    }

    @Override
    public String getRole() {
        return principal().getRole();
    }

    @Override
    public String getToken() {
        return principal().getToken();
    }

    private AuthPrincipal principal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof AuthPrincipal)) {
            throw new IllegalStateException("No authenticated user in SecurityContext");
        }
        return (AuthPrincipal) auth.getPrincipal();
    }
}
