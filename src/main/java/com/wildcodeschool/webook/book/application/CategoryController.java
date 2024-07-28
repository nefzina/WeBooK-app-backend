package com.wildcodeschool.webook.book.application;

import com.wildcodeschool.webook.book.domain.service.CategoryService;
import com.wildcodeschool.webook.book.domain.entity.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    List<Category> readAll() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/categories")
    public Category create(@RequestBody Category newCategory) {
        return categoryService.addCategory(newCategory);
    }

    @PutMapping("/categories/{id}")
    public Category edit(@RequestBody Category newCategory, @PathVariable Long id) {
        return categoryService.updateCategory(newCategory, id);
    }

    @DeleteMapping("/categories/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
