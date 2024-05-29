package com.example.newsservice.mapper;

import com.example.newsservice.model.Comment;
import com.example.newsservice.service.NewsService;
import com.example.newsservice.service.UserService;
import com.example.newsservice.web.model.UpsertCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentClassDelegate implements CommentMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;

    @Override
    public Comment requestToComment(UpsertCommentRequest request) {
        Comment comment = new Comment();
        comment.setCommentText(request.getCommentText());
        comment.setUser(userService.findById(request.getUserId()));
        comment.setNews(newsService.findById(request.getNewsId()));
        return comment;
    }

    @Override
    public Comment requestToComment(Long commentId, UpsertCommentRequest request) {
        Comment comment = requestToComment(request);
        comment.setId(commentId);
        return comment;
    }

}
