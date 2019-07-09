package com.bs.library.mapper;

import com.bs.library.dto.BookDTO;
import com.bs.library.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    BookDTO toBookDTO(Book book);

    List<BookDTO> toBookDTOs(List<Book> books);

    Book toBook(BookDTO bookDTO);

}
