package com.geekbrains.book.store.beans;

import com.geekbrains.book.store.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartTest {
    Book book;
    Cart cart;

    {
        book = new Book(1L, "a", "b", new BigDecimal(10), 1994, Book.Genre.FANTASY);
    }

    @BeforeEach
    public void initEach() {
        cart = new Cart();
        cart.init();
    }

    @Test
    public void testAdd() {
        cart.add(book);

        assertEquals(1, cart.getItems().size());
    }

    @Test
    public void testDecrement() {
        cart.add(book);
        cart.add(book);
        cart.decrement(book);

        assertEquals(1, cart.getItems().size());
        assertEquals(new BigDecimal(10), cart.getPrice());
    }

    @Test
    public void testRemoveByProductId() {
        cart.add(book);
        cart.removeByProductId(book.getId());

        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    public void testPrice() {
        cart.add(book);
        cart.add(book);
        cart.add(book);

        assertEquals(new BigDecimal(30), cart.getPrice());
    }

}