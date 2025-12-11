package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IAuthService;
import com.pragma.powerup.domain.exception.InvalidCredentialsException;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IJwtProviderPort;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.util.RoleConstants;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthUseCase implements IAuthService {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final IJwtProviderPort jwtTokenGenerator;

    @Override
    public String login(String email, String password) {
        User user = userPersistencePort.findByEmail(email);
        if (user == null || !passwordEncoderPort.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String roleName = mapRoleIdToName(user.getRoleId());
        return jwtTokenGenerator.generateToken(user.getId(), user.getEmail(), roleName);
    }

    private String mapRoleIdToName(Long roleId) {
        if (RoleConstants.ROLE_ADMIN.equals(roleId)) return "ADMIN";
        if (RoleConstants.ROLE_OWNER.equals(roleId)) return "OWNER";
        if (RoleConstants.ROLE_EMPLOYEE.equals(roleId)) return "EMPLOYED";
        if (RoleConstants.ROLE_CLIENT.equals(roleId)) return "CLIENT";
        throw new IllegalArgumentException("Unknown role id: " + roleId);
    }
}
