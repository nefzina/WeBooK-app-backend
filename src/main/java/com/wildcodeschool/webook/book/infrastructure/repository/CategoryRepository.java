package com.wildcodeschool.webook.book.infrastructure.repository;

import com.wildcodeschool.webook.book.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
