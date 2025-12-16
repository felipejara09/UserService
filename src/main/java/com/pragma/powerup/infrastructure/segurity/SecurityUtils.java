package com.pragma.powerup.infrastructure.segurity;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {
    private SecurityUtils() {}

    public static Long getUserId() {
        return principal().getUserId();
    }

    public static String getToken() {
        return principal().getToken(); // raw token
    }

    public static String getRole() {
        return principal().getRole();
    }

    private static AuthPrincipal principal() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof AuthPrincipal)) {
            throw new IllegalStateException("No authenticated user in SecurityContext");
        }
        return (AuthPrincipal) auth.getPrincipal();
    }
}
