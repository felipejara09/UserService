package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.AuthRequestDto;
import com.pragma.powerup.application.dto.response.AuthResponseDto;
import com.pragma.powerup.application.handler.IAuthHandler;
import com.pragma.powerup.domain.api.IAuthService;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthHandler implements IAuthHandler {

    private final IAuthService authService;
    private final IUserPersistencePort userPersistencePort;

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        String token = authService.login(authRequestDto.getEmail(), authRequestDto.getPassword());
        return new AuthResponseDto(token);
    }
}
