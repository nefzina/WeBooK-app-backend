package com.wildcodeschool.webook.auth;

import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.book.domain.entity.Book;
import com.wildcodeschool.webook.book.domain.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testUserId() {
        Long id = 1L;
        user.setId(id);
        assertEquals(user.getId(), id);
    }

    @Test
    public void testUserEmail() {
        String email = "test@mail.com";
        user.setEmail(email);
        assertEquals(user.getEmail(), email);
    }

    @Test
    public void testUserBooks() {
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        Book book2 = new Book();

        books.add(book1);
        books.add(book2);
        user.setBooks(books);
        assertEquals(user.getBooks(), books);
    }
}
