package com.example.newsservice.mapper;

import com.example.newsservice.model.News;
import com.example.newsservice.web.model.NewsListResponse;
import com.example.newsservice.web.model.NewsResponse;
import com.example.newsservice.web.model.NewsResponseWithoutComments;
import com.example.newsservice.web.model.UpsertNewsRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {

    News requestToNews(UpsertNewsRequest request);

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, UpsertNewsRequest request);

    NewsResponse newsToResponse(News news);

    default NewsListResponse newListToNewsListResponse(List<News> news) {
        NewsListResponse response = new NewsListResponse();
        List<NewsResponseWithoutComments> newsWithoutCommentsList = new ArrayList<>();
        for(News newsList : news) {
            newsWithoutCommentsList.add(new NewsResponseWithoutComments(newsList.getId(),newsList.getNewsText(),(long)newsList.getComments().size()));
        }
        response.setNews(newsWithoutCommentsList);

        return response;
    }
}
