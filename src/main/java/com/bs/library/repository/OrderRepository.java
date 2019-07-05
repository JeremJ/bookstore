package com.bs.library.repository;


import com.bs.library.entity.Order;
import com.bs.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

}
