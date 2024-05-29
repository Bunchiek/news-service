package com.example.newsservice.service;

import com.example.newsservice.model.Category;
import com.example.newsservice.web.model.filter.CategoryFilter;

import java.util.List;

public interface CategoryService {

    List<Category> findAll(CategoryFilter filter);
    Category findById(Long id);
    Category save(Category category);
    Category update(Category category);
    void deleteById(Long id);
}
