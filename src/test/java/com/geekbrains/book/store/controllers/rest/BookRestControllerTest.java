package com.geekbrains.book.store.controllers.rest;

import com.geekbrains.book.store.entities.Book;
import com.geekbrains.book.store.services.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerTest {

    List<Book> books;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    {
        books = new ArrayList<>();
        Book _1 = new Book();
        _1.setId(1L);
        _1.setDescription("sdf");
        _1.setGenre(Book.Genre.DETECTIVE);
        _1.setPrice(new BigDecimal(12));
        _1.setTitle("Title");
        _1.setPublishYear(1);
        Book _2 = new Book();
        _2.setId(2L);
        _2.setDescription("sdf2");
        _2.setGenre(Book.Genre.DETECTIVE);
        _2.setPrice(new BigDecimal(22));
        _2.setTitle("Title2");
        _2.setPublishYear(2);
        Book _3 = new Book();
        _3.setId(1L);
        _3.setDescription("sdf3");
        _3.setGenre(Book.Genre.FANTASY);
        _3.setPrice(new BigDecimal(32));
        _3.setTitle("Title3");
        _3.setPublishYear(3);
        books.add(_1);
        books.add(_2);
        books.add(_3);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        when(bookService.findAll())
                .thenReturn(books);
        this.mvc.perform(
                get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].title", is(books.get(0).getTitle())));
    }

    @Test
    public void testGetBookById() throws Exception {
        when(bookService.findById(1L))
                .thenReturn(books.get(0));
        this.mvc.perform(
                get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(books.get(0).getTitle())))
                .andExpect(jsonPath("$.publishYear", is(books.get(0).getPublishYear())));
    }

    @Test
    public void testDeleteAllBooks() throws Exception {
        when(bookService.findAll())
                .thenReturn(null);
        this.mvc.perform(
                delete("/api/v1/books"))
                .andExpect(status().isOk());
        verify(bookService, Mockito.times(1)).deleteAll();
    }
}