package com.bs.library.book;

import com.bs.library.book.Book;
import com.bs.library.book.BookDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    BookDTO toBookDTO(Book book);

    List<BookDTO> toBookDTOs(List<Book> books);

    Book toBook(BookDTO bookDTO);

}
