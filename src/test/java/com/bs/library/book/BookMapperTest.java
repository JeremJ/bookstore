package com.bs.library.book;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Before;
import org.junit.Test;

import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookMapperTest {

    private BookMapper bookMapper;

    @Before
    public void setUp() {
        bookMapper = Mappers.getMapper(BookMapper.class);
    }

    @Test
    public void toBookDto_returnsMappedObject_True() {
        //given
        Book book = new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", new BigDecimal(50.99), "qwerty", "qwerty", "qwerty");
        //when
        BookDTO mapperResult = bookMapper.toBookDTO(book);
        //then
        assertThat(mapperResult.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    public void toBook_returnsMappedObject_True() throws JsonProcessingException {
        //given
        BookDTO bookDTO = new BookDTO(1L, 7576575, "Czysty Kod", "Robert C. Martin", new BigDecimal(50.99), "qwerty", "qwerty", null);
        //when
        Book book = bookMapper.toBook(bookDTO);
        //then
        assertThat(book.getAuthor()).isEqualTo(bookDTO.getAuthor());
        assertThat(book.getDescription()).isNull();
    }

    @Test
    public void toBookDtos_returnsMappedObject_True() throws JsonProcessingException {
        //given
        List<Book> books = Arrays.asList(
                new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", new BigDecimal(50.99), "qwerty", "qwerty", "qwerty"),
                new Book(2L, 9432123, "Ogniem i mieczem", "Henryk Sienkiewicz", new BigDecimal(99.99), "qwerty", "qwerty", "qwerty")
        );
        //when
        List<BookDTO> mapperResult = bookMapper.toBookDTOs(books);
        //then
        assertThat(mapperResult.get(0).getAuthor()).isEqualTo(books.get(0).getAuthor());
        assertThat(mapperResult).hasSize(2);
    }
}
