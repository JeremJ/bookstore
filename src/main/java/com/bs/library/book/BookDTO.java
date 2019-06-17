package com.bs.library.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private Integer isbn;
    private String title;
    private String author;
    private BigDecimal price;
    private String genre;
    private String publisher;
    private String description;
}