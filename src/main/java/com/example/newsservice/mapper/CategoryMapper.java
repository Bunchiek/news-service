package com.example.newsservice.mapper;

import com.example.newsservice.model.Category;
import com.example.newsservice.web.model.CategoryListResponse;
import com.example.newsservice.web.model.CategoryResponse;
import com.example.newsservice.web.model.UpsertCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category requestToCategory(UpsertCategoryRequest request);

    @Mapping(source = "categoryId", target = "id")
    Category requestToCategory(Long categoryId, UpsertCategoryRequest request);

    CategoryResponse categoryToResponse(Category category);

    default CategoryListResponse categoryListToResponseList(List<Category> categories){
        CategoryListResponse response = new CategoryListResponse();

        response.setCategories(categories.stream()
                .map(this::categoryToResponse).toList());

        return response;
    }
}
