package com.bs.library.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    @NotNull
    private Integer isbn;
    @NotEmpty
    @Size(max = 50)
    private String title;
    @NotEmpty
    private String author;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal price;
    @NotEmpty
    private String genre;
    @NotEmpty
    private String publisher;
    @Size(max = 200)
    private String description;
    @DecimalMin("0")
    private Integer quantity;
    private Integer version;
}
