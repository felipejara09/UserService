package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.EmployedRequestDto;
import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.dto.response.UserResponseDto;
import com.pragma.powerup.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(
            summary = "Crear propietario",
            description = "Permite al usuario Administrador crear la cuenta de un propietario de restaurante."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propietario creado correctamente",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Correo ya registrado",
                    content = @Content)
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("admin/owners")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDto> createOwner(
            @Valid @RequestBody UserRequestDto ownerRequestDto) {

        UserResponseDto response = userHandler.createOwner(ownerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/restaurants/{restaurantId}/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEmployee(
            @PathVariable Long restaurantId,
            @Valid @RequestBody EmployedRequestDto request
    ) {
        userHandler.createEmployed(request, restaurantId);
    }
}