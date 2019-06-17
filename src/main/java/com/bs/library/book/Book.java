package com.bs.library.book;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer isbn;
    private String title;
    private String author;
    private BigDecimal price;
    private String genre;
    private String publisher;
    private String description;
}
