package com.bs.library.dto;

import com.bs.library.entity.Book;
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
    private Book book;
    @DecimalMin("0")
    private Integer quantity;
    @DecimalMin("0.01")
    private BigDecimal totalPrice;

}
