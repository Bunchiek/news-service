package com.example.newsservice.service;

import com.example.newsservice.model.News;
import com.example.newsservice.web.model.filter.NewsFilter;

import java.util.List;

public interface NewsService {
    List<News> filterBy(NewsFilter filter);
    List<News> findAll(NewsFilter filter);
    News findById(Long id);
    News save(News news);
    News update(News news);
    void deleteById(Long id);
}
