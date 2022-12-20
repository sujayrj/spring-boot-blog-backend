package com.jeppu.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class UserDTO {
    private Long id;

    @NotEmpty(message = "name cannot be empty")
    @Size(min = 5, max = 20, message = "name cannot be less than 5 or greater than 20 characters")
    private String name;

    @Email
    private String email;

    @NotEmpty(message = "password cannot be empty")
    private String password;

    @NotEmpty(message = "about cannot be empty")
    private String about;
}
