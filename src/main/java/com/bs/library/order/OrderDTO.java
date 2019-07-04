package com.bs.library.order;

import com.bs.library.book.Book;
import com.bs.library.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    @NotNull
    @JsonIgnore
    private User user;
    @NotNull
    private Book book;
    @DecimalMin("0")
    private Integer quantity;
    @DecimalMin("0.01")
    private BigDecimal totalPrice;

}
