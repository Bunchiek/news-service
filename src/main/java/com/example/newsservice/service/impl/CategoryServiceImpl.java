package com.example.newsservice.service.impl;

import com.example.newsservice.exception.EntityNotFoundException;
import com.example.newsservice.model.Category;
import com.example.newsservice.repository.CategoryRepository;
import com.example.newsservice.service.CategoryService;
import com.example.newsservice.utils.BeanUtils;
import com.example.newsservice.web.model.filter.CategoryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll(CategoryFilter filter) {
        return categoryRepository.findAllFilterBy(PageRequest.of(filter.getPageNumber(),
                filter.getPageSize())).getContent();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Категория с ID {0} не найден!", id)));
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        Category existedCategory = findById(category.getId());

        BeanUtils.copyNonNullProperties(category, existedCategory);

        return categoryRepository.save(existedCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
