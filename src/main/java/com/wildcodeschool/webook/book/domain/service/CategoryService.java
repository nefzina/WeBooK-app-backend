package com.wildcodeschool.webook.book.domain.service;

import com.wildcodeschool.webook.book.infrastructure.exception.NotFoundException;
import com.wildcodeschool.webook.book.infrastructure.repository.CategoryRepository;
import com.wildcodeschool.webook.book.domain.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public Category getOneCategory(Long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Category addCategory(Category newCategory) {
        return repository.save(newCategory);
    }

    public Category updateCategory(Category newCategory, Long id) {
        return repository.findById(id)
                .map(category -> {
                    category.setType((newCategory.getType()));
                    category.setUsers(newCategory.getUsers());
                    return repository.save(category);
                })
                .orElseThrow(() -> new NotFoundException());
    }

    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
