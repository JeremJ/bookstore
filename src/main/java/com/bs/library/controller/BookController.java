package com.bs.library.controller;

import com.bs.library.dto.BookDTO;
import com.bs.library.service.BookService;
import com.bs.library.model.SearchQueryParams;
import com.bs.library.entity.Book;
import com.bs.library.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    //asAuthority(T(com.bs.library.model.RoleType).ADMIN)
    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority(T(com.bs.library.model.RoleType).ADMIN) or hasAuthority(T(com.bs.library.model.RoleType).CUSTOMER)")
    public ResponseEntity<List<BookDTO>> allBooks(Pageable pageable) {
        Page<Book> page = bookService.allBooksPageable(pageable);
        return ResponseEntity.ok(bookMapper.toBookDTOs(page.getContent()));
    }

    @PostMapping
    @PreAuthorize("hasAuthority(T(com.bs.library.model.RoleType).ADMIN)")
    public ResponseEntity addBook(@Valid @RequestBody BookDTO bookDTO) {
        bookService.addBook(bookMapper.toBook(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Record inserted Successfully !");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.bs.library.model.RoleType).ADMIN)")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return ResponseEntity.ok(bookMapper.toBookDTO(book));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.bs.library.model.RoleType).ADMIN)")
    public ResponseEntity deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Record deleted Successfully !");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.bs.library.model.RoleType).ADMIN)")
    public ResponseEntity updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        bookService.updateBook(id, bookMapper.toBook(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Record updated Successfully !");
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.bs.library.model.RoleType).ADMIN) or hasAuthority(T(com.bs.library.model.RoleType).CUSTOMER)")
    public ResponseEntity<List<BookDTO>> findAllByField(SearchQueryParams search, Sort sort) {
        List<Book> books = bookService.findByParameter(search, sort);
        return ResponseEntity.ok(bookMapper.toBookDTOs(books));
    }

}
