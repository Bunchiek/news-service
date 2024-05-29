package com.example.newsservice.validation;

import com.example.newsservice.web.model.filter.UserFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class UserFilterValidValidator implements ConstraintValidator<FilterValid, UserFilter> {
    @Override
    public boolean isValid(UserFilter value, ConstraintValidatorContext context) {
        return !ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize());
    }
}
