package com.bs.library.book;

import com.bs.library.exception.BookNotFoundException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class BookService {

    private final BookRepository bookRepository;


    public List<Book> allBooks() {
        return bookRepository.findAll();
    }


    public void addBook(Book book) {
        bookRepository.saveAndFlush(book);
    }

    public Book getBook(Long id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException());
    }

    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException();
        }

    }

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

}
