package com.bs.library.book;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    BookDTO toBookDTO(Book book);

    List<BookDTO> toBookDTOs(List<Book> books);

    Book toBook(BookDTO bookDTO);

}
