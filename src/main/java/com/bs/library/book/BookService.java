package com.bs.library.book;

import com.bs.library.exception.BookNotFoundException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class BookService {

    private final BookRepository bookRepository;


    public List<Book> allBooks() throws BookNotFoundException {
        if (bookRepository.findAll().isEmpty()) {
            throw new BookNotFoundException();
        } else
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
        } else throw new BookNotFoundException();
    }

}
