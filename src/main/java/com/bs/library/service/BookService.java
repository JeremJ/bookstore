package com.bs.library.service;

import com.bs.library.model.BookSpecification;
import com.bs.library.model.SearchQueryParams;
import com.bs.library.entity.Book;
import com.bs.library.exception.BookOutOfStockException;
import com.bs.library.exception.BookNotFoundException;
import com.bs.library.repository.BookRepository;
import lombok.Data;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bs.library.config.CacheConfig.BOOK_CACHEVALUE;

@Data
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Cacheable(value = BOOK_CACHEVALUE)
    public Page<Book> allBooksPageable(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        return page;
    }

    public List<Book> findByParameter(SearchQueryParams search, Sort sort) {
        Specification<Book> specification = Specification.where(BookSpecification.withIsbn(search.getIsbn())
                .or(BookSpecification.withTitle(search.getTitle()))
                .or(BookSpecification.withAuthor(search.getAuthor()))
                .or(BookSpecification.withPrice(search.getPrice()))
                .or(BookSpecification.withGenre(search.getGenre()))
                .or(BookSpecification.withPublisher(search.getPublisher()))
                .or(BookSpecification.withDescription(search.getDescription()))
        );
        return bookRepository.findAll(specification, sort);
    }

    @CacheEvict(value = BOOK_CACHEVALUE, allEntries = true)
    public void addBook(Book book) {
        bookRepository.saveAndFlush(book);
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @CacheEvict(value = BOOK_CACHEVALUE, allEntries = true)
    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException();
        }
    }

    @CacheEvict(value = BOOK_CACHEVALUE, allEntries = true)
    public void updateBook(Long id, Book book) {
        Book currentBook;
        currentBook = bookRepository.findById(id).map(
                book1 -> {
                    book1.setIsbn(book.getIsbn());
                    book1.setTitle(book.getTitle());
                    book1.setAuthor(book.getAuthor());
                    book1.setPrice(book.getPrice());
                    book1.setGenre(book.getGenre());
                    book1.setPublisher(book.getPublisher());
                    book1.setDescription(book.getDescription());
                    return book1;
                }
        ).orElseThrow(BookNotFoundException::new);

        bookRepository.save(currentBook);
    }

    public Boolean checkBookQuantity(Book book, Integer quantity) {
        if ((book.getQuantity() - quantity) >= 0) {
            return true;
        } else {
            throw new BookOutOfStockException();
        }
    }
}
