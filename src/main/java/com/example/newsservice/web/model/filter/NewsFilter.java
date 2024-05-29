package com.example.newsservice.web.model.filter;

import com.example.newsservice.validation.FilterValid;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@FilterValid
public class NewsFilter {

    private Integer pageSize;
    private Integer pageNumber;
    private Long categoryId;
    private Long userId;
}
