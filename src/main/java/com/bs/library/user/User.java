package com.bs.library.user;

import com.bs.library.order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
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
    private BigDecimal accountBalance;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();
    @Version
    private Integer version;

    public User(String username, String pass, String email, RoleType role, BigDecimal accountBalance) {
        this.username = username;
        this.pass = pass;
        this.email = email;
        this.role = role;
        this.accountBalance = accountBalance;
    }
}