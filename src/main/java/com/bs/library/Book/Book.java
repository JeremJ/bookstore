package com.bs.library.Book;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;
    Long ISBN;
    String title;
    String author;
    Float price;
    String genre;
    String pubh;
    String description;

    public Book() {
    }

    public Book(Long id, Long ISBN, String title, String author, Float price, String genre, String pubh, String description) {
        this.Id = id;
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.price = price;
        this.genre = genre;
        this.pubh = pubh;
        this.description = description;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getISBN() {
        return ISBN;
    }

    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPubh() {
        return pubh;
    }

    public void setPubh(String pubh) {
        this.pubh = pubh;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "Id=" + Id +
                ", ISBN=" + ISBN +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", genre='" + genre + '\'' +
                ", pubh='" + pubh + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
