package com.bs.library.Book;

import lombok.*;

import javax.persistence.*;

@Data
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Integer ISBN;
    private String title;
    private String author;
    private Float price;
    private String genre;
    private String pubh;
    private String description;
}
