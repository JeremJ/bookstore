package com.bs.library.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @PreAuthorize("hasAuthority(T(com.bs.library.user.RoleType).CUSTOMER)")
    @PostMapping("/{id}/{quantity}")
    public ResponseEntity createOrder(@PathVariable Long id, @PathVariable Integer quantity) {
        orderService.createOrder(id, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Record created Successfully !");
    }

    @PreAuthorize("hasAuthority(T(com.bs.library.user.RoleType).CUSTOMER)")
    @GetMapping
    public ResponseEntity getUserOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orderMapper.toOrderDTOs(orders));
    }

    @PreAuthorize("hasAuthority(T(com.bs.library.user.RoleType).ADMIN)")
    @GetMapping("/all")
    public ResponseEntity getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orderMapper.toOrderDTOs(orders));
    }

}
