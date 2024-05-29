package com.example.newsservice.validation;

import com.example.newsservice.web.model.filter.NewsFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class NewsFilterValidValidator implements ConstraintValidator<FilterValid, NewsFilter> {
    @Override
    public boolean isValid(NewsFilter value, ConstraintValidatorContext constraintValidatorContext) {
        return !ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize());
    }

}
