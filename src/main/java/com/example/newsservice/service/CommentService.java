package com.example.newsservice.service;

import com.example.newsservice.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAll();
    Comment findById(Long id);
    Comment save(Comment comment);
    Comment update(Comment comment, Long userId);
    void deleteById(Long id, Long userId);
}
