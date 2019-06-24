package com.bs.library.book;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class BookSpecification {

    public static Specification<Book> withIsbn(Integer isbn) {
        return (root, query, builder) -> builder.equal(root.get("isbn"), isbn);
    }
    public static Specification<Book> withTitle(String title) {
        return (root, query, builder) -> builder.equal(root.get("title"), title);
    }
    public static Specification<Book> withAuthor(String author) {
        return (root, query, builder) -> builder.equal(root.get("author"), author);
    }
    public static Specification<Book> withPrice(BigDecimal price) {
        return (root, query, builder) -> builder.equal(root.get("price"), price);
    }
    public static Specification<Book> withGenre(String genre) {
        return (root, query, builder) -> builder.equal(root.get("genre"), genre);
    }
    public static Specification<Book> withPublisher(String publisher) {
        return (root, query, builder) -> builder.equal(root.get("publisher"), publisher);
    }
    public static Specification<Book> withDescription(String description) {
        return (root, query, builder) -> builder.equal(root.get("description"), description);
    }

}
