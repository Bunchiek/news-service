package com.example.newsservice.web.model;

import com.example.newsservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertUserRequest {
    private String username;
    private String password;
}
