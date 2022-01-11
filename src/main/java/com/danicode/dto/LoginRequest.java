package com.danicode.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
  /*  @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 6,message = "Password cannot be less than 6 characters")*/
     private String email;
    private String password;


}
