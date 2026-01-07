package com.pragma.powerup.application.dto.request;



import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@AllArgsConstructor
public class AuthRequestDto {
    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank(message = "Password must not be empty")
    private String password;
}
