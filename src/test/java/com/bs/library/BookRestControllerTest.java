package com.bs.library;

import com.bs.library.Book.Book;
import com.bs.library.Book.BookRepository;

import com.bs.library.Book.BookRestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @InjectMocks
    BookRestController bookRestController;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(bookRestController)
                .build();
        Book book = new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", 50.99f, "qwerty", "qwerty", "qwerty");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    }

    @Test
    public void addBook_save_Ok() throws Exception {
        Book book = new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", 50.99f, "qwerty", "qwerty", "qwerty");
        String requestJson = asJson(book);
        mockMvc.perform(post("/books/add").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isCreated());
        verify(bookRepository, times(1)).save(any(Book.class));

    }

    @Test
    public void addBook_bookAlreadyExist_Failed() throws Exception {
        Book book = new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", 50.99f, "qwerty", "qwerty", "qwerty");
        String requestJson = asJson(book);
        when(bookRepository.existsById(book.getId())).thenReturn(true);
        mockMvc.perform(post("/books/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isBadRequest());
        verify(bookRepository, times(1)).existsById(book.getId());


    }

    @Test
    public void getBook_bookNotFound_Null() throws Exception {
        mockMvc.perform(get("/books/2"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string("null"));
        verify(bookRepository, times(1)).findById(2L);

    }

    @Test
    public void allBooks_findAll_Ok() throws Exception {
        List<Book> books = Arrays.asList(
                new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", 50.99f, "qwerty", "qwerty", "qwerty"),
                new Book(2L, 9432123, "Ogniem i mieczem", "Henryk Sienkiewicz", 99.99f, "qwerty", "qwerty", "qwerty")
        );
        when(bookRepository.findAll()).thenReturn(books);
        mockMvc.perform(get("/books/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].isbn", is(7576575)))
                .andExpect(jsonPath("$[0].title", is("Czysty Kod")))
                .andExpect(jsonPath("$[0].author", is("Robert C. Martin")))
                .andExpect(jsonPath("$[0].price", is(50.99)))
                .andExpect(jsonPath("$[0].genre", is("qwerty")))
                .andExpect(jsonPath("$[0].pubh", is("qwerty")))
                .andExpect(jsonPath("$[0].description", is("qwerty")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].isbn", is(9432123)))
                .andExpect(jsonPath("$[1].title", is("Ogniem i mieczem")))
                .andExpect(jsonPath("$[1].author", is("Henryk Sienkiewicz")))
                .andExpect(jsonPath("$[1].price", is(99.99)))
                .andExpect(jsonPath("$[1].genre", is("qwerty")))
                .andExpect(jsonPath("$[1].pubh", is("qwerty")))
                .andExpect(jsonPath("$[1].description", is("qwerty")));
        verify(bookRepository, times(1)).findAll();

    }

    @Test
    public void deleteBook_bookNotExist_Failed() throws Exception {
        when(bookRepository.existsById(1L)).thenReturn(false);
        mockMvc.perform(delete("/books/{id}", 1))
                .andExpect(status().isBadRequest());
        verify(bookRepository, times(1)).existsById(1L);
    }

    @Test
    public void updateBook_bookNotExist_Failed() throws Exception {
        Book book = new Book(0L, 7576575, "Czysty Kod", "Robert C. Martin", 50.99f, "qwerty", "qwerty", "qwerty");
        String requestJson = asJson(book);
        when(bookRepository.existsById(book.getId())).thenReturn(false);
        mockMvc.perform(put("/books/{id}", book.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isBadRequest());
        verify(bookRepository, times(1)).existsById(book.getId());
    }

    public static String asJson(final Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(obj);
        return requestJson;
    }

}
