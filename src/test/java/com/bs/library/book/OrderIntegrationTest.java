package com.bs.library.book;

import com.bs.library.exception.BookOutOfStockException;
import com.bs.library.exception.UserAccountBalanceException;
import com.bs.library.order.Order;
import com.bs.library.order.OrderController;
import com.bs.library.order.OrderMapper;
import com.bs.library.order.OrderService;
import com.bs.library.user.RoleType;
import com.bs.library.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test", authorities = {"ADMIN"})
public class OrderIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderController orderController;


    @Before
    public void before() {
        orderController = new OrderController(orderService, orderMapper);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(username = "test", authorities = {"CUSTOMER"})
    public void createOrder_BookOutOfStock_ExceptionThrown() throws Exception {
        //given
        Book book = new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", new BigDecimal(50.99), "qwerty", "qwerty", "qwerty", 1, 0);
        //then
        doThrow(new BookOutOfStockException()).when(orderService).createOrder(1L, 5);
        mockMvc.perform(post("/order/{id}/{quantity}", 1, 5))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Product is out of stock!"));
        verify(orderService, times(1)).createOrder(1L, 5);
    }

    @Test
    @WithMockUser(username = "test", authorities = {"ADMIN"})
    public void getAllOrders_ValidData_StatusOk() throws Exception {
        //given
        Book book = new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", new BigDecimal(50.99), "qwerty", "qwerty", "qwerty", 1, 0);
        User user = new User(1L, "qwert", "qwerty@gmail.com", "qwerty1", RoleType.CUSTOMER, new BigDecimal(100), null, 1);
        List<Order> orders = Arrays.asList(
                new Order(1L, user, book, 1, new BigDecimal(2)),
                new Order(1L, user, book, 1, new BigDecimal(2))
        );
        //when
        when(orderService.getAllOrders()).thenReturn(orders);
        mockMvc.perform(get("/order/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        verify(orderService, times(1)).getAllOrders();

    }

    @Test
    @WithMockUser(username = "test", authorities = {"CUSTOMER"})
    public void createOrder_AccountBalance_ExceptionThrown() throws Exception {
        //given
        Book book = new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", new BigDecimal(20), "qwerty", "qwerty", "qwerty", 1, 0);

        doThrow(new UserAccountBalanceException()).when(orderService).createOrder(1L, 1);
        //then
        mockMvc.perform(post("/order/{id}/{quantity}", 1, 1))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Check your account balance!"));
        verify(orderService, times(1)).createOrder(1L, 1);

    }

}

