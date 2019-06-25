package com.bs.library.security;

import com.bs.library.user.RoleType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpForm {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;

    private RoleType role;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
