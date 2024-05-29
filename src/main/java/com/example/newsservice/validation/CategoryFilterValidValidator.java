package com.example.newsservice.validation;

import com.example.newsservice.web.model.filter.CategoryFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class CategoryFilterValidValidator implements ConstraintValidator<FilterValid, CategoryFilter> {
    @Override
    public boolean isValid(CategoryFilter value, ConstraintValidatorContext constraintValidatorContext) {
        return !ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize());
    }

}
