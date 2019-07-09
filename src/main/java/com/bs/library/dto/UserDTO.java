package com.bs.library.dto;

import com.bs.library.entity.Order;
import com.bs.library.model.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

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
    @DecimalMin("0")
    private BigDecimal accountBalance = new BigDecimal(10);
    @JsonIgnore
    private Set<Order> orders;
    private Timestamp version;
}
