package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.dto.response.UserResponseDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.mapper.IUserRequestMapper;
import com.pragma.powerup.domain.api.IUserService;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.util.RoleConstants;
import com.pragma.powerup.infrastructure.segurity.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserService userServicePort;
    private final IUserRequestMapper userRequestMapper;


    @Override
    public UserResponseDto createOwner(UserRequestDto userRequestDto) {

        User user = userRequestMapper.toUser(userRequestDto);
        user.setRoleId(RoleConstants.ROLE_OWNER);
        userServicePort.createOwner(user);

        return new UserResponseDto(
                user.getId(),
                user.getName() + " " + user.getLastName(),
                user.getEmail()
        );
    }

    @Override
    public void createEmployed(UserRequestDto dto, Long restaurantId) {
        String token = SecurityUtils.getToken();
        User employee = userRequestMapper.toEmployed(dto);
        userServicePort.createEmployed(employee, restaurantId, token);
    }

    @Override
    public void registerClient(UserRequestDto dto) {
        userServicePort.registerClient(userRequestMapper.toClient(dto));
    }

}