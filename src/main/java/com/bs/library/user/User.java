package com.bs.library.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String pass;
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleType role;

    public User(String username, String pass, String email, RoleType role) {
        this.username = username;
        this.pass = pass;
        this.email = email;
        this.role = role;
    }
}