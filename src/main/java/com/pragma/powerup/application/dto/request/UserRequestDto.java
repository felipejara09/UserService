package com.pragma.powerup.application.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Data
public class UserRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotBlank
    private String documentId;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private Long roleId;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
