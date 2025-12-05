package com.pragma.powerup.domain.util;

import java.util.Set;

public final class RoleConstants {

    private RoleConstants() {}

    public static final Long ROLE_ADMIN     = 1L;
    public static final Long ROLE_OWNER     = 2L;
    public static final Long ROLE_EMPLOYEE  = 3L;
    public static final Long ROLE_CLIENT    = 4L;

    public static final Set<Long> VALID_ROLES = Set.of(
            ROLE_ADMIN,
            ROLE_OWNER,
            ROLE_EMPLOYEE,
            ROLE_CLIENT
    );
}
