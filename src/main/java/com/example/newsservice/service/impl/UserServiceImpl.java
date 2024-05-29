package com.example.newsservice.service.impl;

import com.example.newsservice.exception.EntityNotFoundException;
import com.example.newsservice.model.User;
import com.example.newsservice.repository.CommentRepository;
import com.example.newsservice.repository.NewsRepository;
import com.example.newsservice.repository.UserRepository;
import com.example.newsservice.service.UserService;
import com.example.newsservice.utils.BeanUtils;
import com.example.newsservice.web.model.filter.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly=true)
    public List<User> findAll(UserFilter filter) {
        return userRepository.findAllFilterBy(PageRequest.of(filter.getPageNumber(),
                filter.getPageSize())).getContent();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Пользователь с ID {0} не найден!", id)));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User existedUser = findById(user.getId());

        BeanUtils.copyNonNullProperties(user,existedUser);

        return userRepository.save(existedUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
