package com.example.newsservice.service;

import com.example.newsservice.model.User;
import com.example.newsservice.web.model.filter.UserFilter;

import java.util.List;

public interface UserService {

    List<User> findAll(UserFilter filter);
    User findById(Long id);
    User save(User user);
    User update(User user);
    void deleteById(Long id);
}
