package com.example.newsservice.web.controller;

import com.example.newsservice.aop.Secured;
import com.example.newsservice.mapper.NewsMapper;
import com.example.newsservice.model.News;
import com.example.newsservice.service.NewsService;
import com.example.newsservice.web.model.filter.NewsFilter;
import com.example.newsservice.web.model.NewsListResponse;
import com.example.newsservice.web.model.NewsResponse;
import com.example.newsservice.web.model.UpsertNewsRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final NewsMapper newsMapper;


    @GetMapping("/filter")
    public ResponseEntity<NewsListResponse> filterBy(NewsFilter filter) {
        return ResponseEntity.ok(
                newsMapper.newListToNewsListResponse(newsService.filterBy(filter))
        );
    }

    @GetMapping
    public ResponseEntity<NewsListResponse> findAll(@Valid NewsFilter filter) {
        return ResponseEntity.ok(
                newsMapper.newListToNewsListResponse(newsService.findAll(filter)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(newsMapper.newsToResponse(newsService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<NewsResponse> create(@RequestBody @Valid UpsertNewsRequest request) {
        News newNews = newsService.save(newsMapper.requestToNews(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.newsToResponse(newNews));
    }

    @PutMapping("/{id}")
    @Secured(checkOwnership = true)
    public ResponseEntity<NewsResponse> update(@PathVariable("id") Long newsId,
                                               @RequestBody @Valid UpsertNewsRequest request) {
        News updatedNews = newsService.update(newsMapper.requestToNews(newsId, request));
        return ResponseEntity.ok(newsMapper.newsToResponse(updatedNews));
    }
    @DeleteMapping("/{id}")
    @Secured(roles = {"ROLE_ADMIN", "ROLE_MODERATOR"}, checkOwnership = true)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
