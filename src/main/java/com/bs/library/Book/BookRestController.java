package com.bs.library.Book;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@RestController
@RequestMapping("/books")
public class BookRestController {

    @Autowired
    BookRepository bookRepository;


    @GetMapping("/all")
    List<Book> allBooks() {
        return bookRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        if (bookRepository.existsById(book.getId())) {
            return new ResponseEntity<>("It already exists !" + book.getId(), HttpStatus.BAD_REQUEST);
        } else {
            bookRepository.save(book);
            return new ResponseEntity<>("Record inserted Successfully !" + book.getId(), HttpStatus.CREATED);
        }
    }

    @GetMapping("/{id}")
    Optional<Book> getBook(@PathVariable Long id) {
        return bookRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteBook(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return new ResponseEntity<>("Record deleted Successfully !", HttpStatus.OK);
        } else return new ResponseEntity<>("Record does not exist", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book book) {
        if (bookRepository.existsById(id)) {
            Book currentBook = bookRepository.getOne(id);
            currentBook.setISBN(book.getISBN());
            currentBook.setTitle(book.getTitle());
            currentBook.setAuthor(book.getAuthor());
            currentBook.setPrice(book.getPrice());
            currentBook.setGenre(book.getGenre());
            currentBook.setPubh(book.getPubh());
            currentBook.setDescription(book.getDescription());
            bookRepository.save(currentBook);
            return new ResponseEntity<>("Record updated Successfully !", HttpStatus.OK);
        } else return new ResponseEntity<>("Record does not exist", HttpStatus.BAD_REQUEST);
    }

}
