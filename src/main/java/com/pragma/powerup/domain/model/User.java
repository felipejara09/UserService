package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String lastName;
    private String documentId;
    private LocalDate birthDate;
    private String phoneNumber;
    private Long roleId;
    private String email;
    private String password;
    private Long restaurantId;
}
