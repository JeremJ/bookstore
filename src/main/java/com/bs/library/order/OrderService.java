package com.bs.library.order;

import com.bs.library.book.Book;
import com.bs.library.book.BookService;
import com.bs.library.exception.OrderProcessedException;
import com.bs.library.user.User;
import com.bs.library.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final BookService bookService;

    private final UserService userService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createOrder(Long id, Integer quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.getUserByUsername(currentPrincipalName);
        return orderRepository.findByUser(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
