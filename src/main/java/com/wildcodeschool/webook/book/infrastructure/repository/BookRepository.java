package com.wildcodeschool.webook.book.infrastructure.repository;

import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.book.domain.entity.Book;
import com.wildcodeschool.webook.book.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {
List<Book> findBooksByBookCategory(Category bookCategory);
List<Book> findBooksByOwner(User owner);
List<Book> findBooksByNameIsContainingIgnoreCaseAndAuthorIsContainingIgnoreCase(String name, String author);
}
