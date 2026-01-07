package com.pragma.powerup.infrastructure.input.rest;

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
            summary = "Create restaurant owner",
            description = "Allows an administrator to create a restaurant owner account."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Restaurant owner created successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email address is already registered",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. Only administrators can create owners",
                    content = @Content
            )
    })


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("admin/owners")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDto> createOwner(
            @Valid @RequestBody UserRequestDto ownerRequestDto) {

        UserResponseDto response = userHandler.createOwner(ownerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Create employee",
            description = "Allows a restaurant owner to create an employee and assign them to their restaurant."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Employee created successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data or business rules violated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. Only restaurant owners can create employees",
                    content = @Content
            )
    })

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/restaurants/{restaurantId}/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEmployee(
            @PathVariable Long restaurantId,
            @Valid @RequestBody UserRequestDto request
    ) {
        userHandler.createEmployed(request, restaurantId);
    }

    @Operation(
            summary = "Register client",
            description = "Allows a user to register as a client in the platform."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Client registered successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email address is already registered",
                    content = @Content
            )
    })

    @PostMapping("/clients")
    public ResponseEntity<Void> registerClient(@Valid @RequestBody UserRequestDto dto) {
        userHandler.registerClient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Get user phone number",
            description = "Allows authorized users to retrieve the phone number of a specific user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User phone number retrieved successfully",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. User does not have permission to access this resource",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    })


    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYED','OWNER')")
    @GetMapping("/{userId}/phone")
    public ResponseEntity<String> getUserPhone(@PathVariable Long userId) {
        return ResponseEntity.ok(userHandler.getUserPhoneNumber(userId));
    }





}