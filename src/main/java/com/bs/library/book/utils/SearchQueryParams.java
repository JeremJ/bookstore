package com.bs.library.book.utils;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SearchQueryParams {
    private Integer isbn;
    private String title;
    private String author;
    private BigDecimal price;
    private String genre;
    private String publisher;
    private String description;
}
