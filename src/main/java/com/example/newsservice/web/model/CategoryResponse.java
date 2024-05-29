package com.example.newsservice.web.model;

import com.example.newsservice.model.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String categoryName;
    private String categoryDescription;
    private List<News> news = new ArrayList<>();
}
