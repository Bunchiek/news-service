package com.example.newsservice.web.model.filter;

import com.example.newsservice.validation.FilterValid;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@FilterValid
public class UserFilter {

    private Integer pageSize;
    private Integer pageNumber;
}
