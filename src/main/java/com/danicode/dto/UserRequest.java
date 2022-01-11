package com.danicode.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserRequest {
    @NotEmpty
    @Size(min = 2, message = "Username should have at least 2 characters")
    private String firstName;
    private String lastName;
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 6,message = "Password cannot be less than 6 characters")
    private String password;
    private String confirmPassword;
}
