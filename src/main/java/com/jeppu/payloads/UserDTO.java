package com.jeppu.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@NoArgsConstructor
@Data
public class UserDTO {
    private Long id;

    private String name;
    private String email;
    private String password;
    private String about;
}
