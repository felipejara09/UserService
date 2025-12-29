package com.pragma.powerup.application.handler;


import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.dto.response.UserResponseDto;


public interface IUserHandler {

   UserResponseDto createOwner(UserRequestDto userRequestDto);
   void createEmployed(UserRequestDto dto, Long restaurantId);
   void registerClient(UserRequestDto dto);
   String getUserPhoneNumber(Long userId);
}