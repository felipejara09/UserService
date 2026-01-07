package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.AuthRequestDto;
import com.pragma.powerup.application.dto.response.AuthResponseDto;
import com.pragma.powerup.application.dto.response.UserResponseDto;
import com.pragma.powerup.application.handler.IAuthHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final IAuthHandler authHandler;

    @Operation(
            summary = "User login",
            description = "Allows a user to authenticate using email and password and receive a JWT token."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User authenticated successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid credentials or invalid input data",
                    content = @Content
            )
    })

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Validated @RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto response = authHandler.login(authRequestDto);
        return ResponseEntity.ok(response);
    }
}
