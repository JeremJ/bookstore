package com.bs.library.controller;

import com.bs.library.entity.Order;
import com.bs.library.mapper.OrderMapper;
import com.bs.library.service.OrderService;
import com.bs.library.model.RoleType;
import com.bs.library.entity.User;
import com.bs.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    private final UserService userService;

    private final OrderMapper orderMapper;

    @PreAuthorize("hasAuthority(T(com.bs.library.model.RoleType).CUSTOMER)")
    @PostMapping("/{id}/{quantity}")
    public ResponseEntity createOrder(@PathVariable Long id, @PathVariable Integer quantity) {
        orderService.createOrder(id, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Record created Successfully !");
    }

    @PreAuthorize("hasAuthority(T(com.bs.library.model.RoleType).ADMIN) or hasAuthority(T(com.bs.library.model.RoleType).CUSTOMER)")
    @GetMapping
    public ResponseEntity getUserOrders(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        if (user.getRole().equals(RoleType.ADMIN)) {
            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.status(HttpStatus.OK).body(orderMapper.toOrderDTOs(orders));
        } else {
            List<Order> orders = orderService.getOrders();
            return ResponseEntity.status(HttpStatus.OK).body(orderMapper.toOrderDTOs(orders));
        }
    }
}
