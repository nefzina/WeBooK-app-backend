package com.wildcodeschool.webook.book;

import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.book.domain.entity.Book;
import com.wildcodeschool.webook.book.domain.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {


        private Book book;

        @BeforeEach
        public void setUp() {
            book = new Book();
        }

        @Test
        public void testBookTitle() {
            String title = "The Wager";
            book.setTitle(title);
            assertEquals(book.getTitle(), title);
        }

        @Test
        public void testBookCategory() {
            Category category = new Category();
            book.setBookCategory(category);
            assertEquals(book.getBookCategory(), category);
        }
}
