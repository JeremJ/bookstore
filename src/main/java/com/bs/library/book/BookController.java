package com.bs.library.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.querydsl.core.types.Predicate;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> allBooks(Pageable pageable) {
        Page<Book> page = bookService.allBooksPageable(pageable);
        return ResponseEntity.ok(bookMapper.toBookDTOs(page.getContent()));
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

    @GetMapping
    public ResponseEntity<List<BookDTO>> findAllByField(@QuerydslPredicate(root = Book.class) Predicate predicate, Sort sort) {
        List<Book> books = bookService.findByParameter(predicate, sort);
        return ResponseEntity.ok(bookMapper.toBookDTOs(books));
    }

}
