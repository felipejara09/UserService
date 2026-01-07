package com.pragma.powerup.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Document ID is required")
    private String documentId;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

}
