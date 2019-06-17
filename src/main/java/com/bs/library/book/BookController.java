package com.bs.library.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    public ResponseEntity<List<BookDTO>> allBooks() {
        return ResponseEntity.ok(bookMapper.toBookDTOs(bookService.allBooks()));
    }

    @PostMapping
    public ResponseEntity addBook(@RequestBody BookDTO bookDTO) {
        bookService.addBook(bookMapper.toBook(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Record inserted Successfully !");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return ResponseEntity.ok(bookMapper.toBookDTO(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Record deleted Successfully !");
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        bookService.updateBook(id, bookMapper.toBook(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Record updated Successfully !");

    }
}
