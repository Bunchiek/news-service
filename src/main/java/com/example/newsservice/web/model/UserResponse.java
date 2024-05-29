package com.example.newsservice.web.model;

import com.example.newsservice.model.Comment;
import com.example.newsservice.model.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String userName;
    private List<News> news;
    private List<Comment> comments;
}
