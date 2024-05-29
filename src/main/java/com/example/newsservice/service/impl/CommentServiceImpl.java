package com.example.newsservice.service.impl;

import com.example.newsservice.aop.Editable;
import com.example.newsservice.exception.EntityNotFoundException;
import com.example.newsservice.model.Comment;
import com.example.newsservice.model.News;
import com.example.newsservice.model.User;
import com.example.newsservice.repository.CommentRepository;
import com.example.newsservice.service.CommentService;
import com.example.newsservice.service.NewsService;
import com.example.newsservice.service.UserService;
import com.example.newsservice.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final NewsService newsService;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Комментарий с ID {0} не найден!", id)));
    }

    @Override
    public Comment save(Comment comment) {
        User user = userService.findById(comment.getUser().getId());
        News news = newsService.findById(comment.getNews().getId());
        comment.setUser(user);
        comment.setNews(news);
        return commentRepository.save(comment);
    }

    @Override
    @Editable
    public Comment update(Comment comment, Long userId) {
        User user = userService.findById(comment.getUser().getId());
        News news = newsService.findById(comment.getNews().getId());

        Comment existedComment = findById(comment.getId());
        BeanUtils.copyNonNullProperties(comment, existedComment);
        existedComment.setUser(user);
        existedComment.setNews(news);
        return commentRepository.save(existedComment);
    }

    @Override
    @Editable
    public void deleteById(Long id, Long userId) {
        commentRepository.deleteById(id);
    }
}
