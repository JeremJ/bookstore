package com.bs.library.service;

import com.bs.library.entity.Book;
import com.bs.library.entity.Order;
import com.bs.library.entity.User;
import com.bs.library.exception.OrderProcessedException;
import com.bs.library.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final BookService bookService;

    private final UserService userService;

    public void createOrder(Long id, Integer quantity) {
        String currentPrincipalName = userService.getCurrentUser();
        Book book = bookService.getBook(id);
        User user = userService.getUserByUsername(currentPrincipalName);
        BigDecimal totalPrice = book.getPrice().multiply(new BigDecimal(quantity));
        if (bookService.checkBookQuantity(book, quantity)
                && userService.checkAccountBalance(user, totalPrice)) {
            Order order = new Order();
            order.setUser(user);
            order.setBook(book);
            order.setQuantity(quantity);
            order.setTotalPrice(totalPrice);
            user.setAccountBalance(user.getAccountBalance().subtract(totalPrice));
            book.setQuantity(book.getQuantity() - quantity);
            userService.updateUser(user.getId(), user);
            bookService.updateBook(book.getId(), book);
            orderRepository.save(order);
        } else throw new OrderProcessedException();
    }

    public List<Order> getOrders() {
        String currentPrincipalName = userService.getCurrentUser();
        User user = userService.getUserByUsername(currentPrincipalName);
        return orderRepository.findByUser(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
