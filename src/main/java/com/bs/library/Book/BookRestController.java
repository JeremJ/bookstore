package com.bs.library.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/book")
public class BookRestController {

    @Autowired
    BookRepository bookRepository;

    BookRestController() {
    }

    @GetMapping("/all")
    List<Book> all() {
        return bookRepository.findAll();
    }

    @PostMapping("/add")
    void newBook(@RequestBody Book book) {
        bookRepository.save(book);
    }

    @GetMapping("/{id}")
    Optional<Book> getBook(@PathVariable Long id) {
        return bookRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    Book updateUser(@PathVariable Long id, @RequestBody Book book) {
        Book currentBook = bookRepository.getOne(id);
        currentBook.setISBN(book.getISBN());
        currentBook.setISBN(book.getISBN());
        currentBook.setTitle(book.getTitle());
        currentBook.setAuthor(book.getAuthor());
        currentBook.setPrice(book.getPrice());
        currentBook.setGenre(book.getGenre());
        currentBook.setPubh(book.getPubh());
        currentBook.setDescription(book.getDescription());
        return bookRepository.save(currentBook);
    }

}
