package com.bs.library.book;

import com.bs.library.exception.BookNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookIntegrationTest {




    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookController bookController;


    @Before
    public void before() {

        bookController = new BookController(bookService, bookMapper);

        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void allBooks_ValidData_StatusOk() throws Exception {

        //given
        List<Book> books = Arrays.asList(
                new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", new BigDecimal(50.99), "qwerty", "qwerty", "qwerty"),
                new Book(2L, 9432123, "Ogniem i mieczem", "Henryk Sienkiewicz", new BigDecimal(99.99), "qwerty", "qwerty", "qwerty")
        );
        when(bookService.allBooks()).thenReturn(books);
        //then
        mockMvc.perform(get("/books"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].isbn", is(7576575)))
                .andExpect(jsonPath("$[0].title", is("Czysty Kod")))
                .andExpect(jsonPath("$[0].author", is("Robert C. Martin")))
                .andExpect(jsonPath("$[0].genre", is("qwerty")))
                .andExpect(jsonPath("$[0].pubh", is("qwerty")))
                .andExpect(jsonPath("$[0].description", is("qwerty")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].isbn", is(9432123)))
                .andExpect(jsonPath("$[1].title", is("Ogniem i mieczem")))
                .andExpect(jsonPath("$[1].author", is("Henryk Sienkiewicz")))
                .andExpect(jsonPath("$[1].genre", is("qwerty")))
                .andExpect(jsonPath("$[1].pubh", is("qwerty")))
                .andExpect(jsonPath("$[1].description", is("qwerty")))
                .andExpect(status().isOk());
        verify(bookService, times(1)).allBooks();

    }

    @Test
    public void getAll_MissingRecords_StatusNotFound() throws Exception {
        //given
        when(bookService.allBooks()).thenThrow(new BookNotFoundException());
        //then
        mockMvc.perform(get("/books"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Record does not exist"));
        verify(bookService, times(1)).allBooks();
    }

    @Test
    public void addBook_ValidData_StatusCreated() throws Exception {
        //given
        Book book = new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", new BigDecimal(50.99), "qwerty", "qwerty", "qwerty");
        String requestJson = asJson(book);
        //then
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Record inserted Successfully !"));

    }

    @Test
    public void getBook_InvalidId_ExceptionThrown() throws Exception {
        //given
        given(bookService.getBook(1L)).willThrow(new BookNotFoundException());
        //then
        mockMvc.perform(get("/books/{id}", 1))
                .andExpect(content().string("Record does not exist"))
                .andExpect(status().isNotFound());
        verify(bookService, times(1)).getBook(1L);

    }

    @Test
    public void getBook_ValidData_ProperJsonReturned() throws Exception {
        //given
        Book book = new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", new BigDecimal(50.99), "qwerty", "qwerty", "qwerty");
        String requestJson = asJson(book);
        given(bookService.getBook(1L)).willReturn(book);
        //then
        mockMvc.perform(get("/books/{id}", 1))
                .andExpect(content().json(requestJson))
                .andExpect(status().isOk());
        verify(bookService, times(1)).getBook(1L);

    }

    @Test
    public void deleteBook_InvalidId_ExceptionThrown() throws Exception {
        //given
        doThrow(new BookNotFoundException()).when(bookService).deleteBook(1L);
        //then
        mockMvc.perform(delete("/books/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Record does not exist"));
        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    public void updateBook_InvalidId_ExceptionThrown() throws Exception {
        //given
        Book book = new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", new BigDecimal(50.99), "qwerty", "qwerty", "qwerty");
        String requestJson = asJson(book);
        doThrow(new BookNotFoundException()).when(bookService).updateBook(1L, book);
        //then
        mockMvc.perform(put("/books/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Record does not exist"));
        verify(bookService, times(1)).updateBook(1L, book);
    }

    public static String asJson(final Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(obj);
        return requestJson;
    }

}