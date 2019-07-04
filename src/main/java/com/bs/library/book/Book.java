package com.bs.library.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book implements Serializable {

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
    private Integer quantity;
    @Version
    private Integer version;
}
