package com.bs.library.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
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
    private String pass;
}
