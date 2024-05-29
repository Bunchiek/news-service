package com.example.newsservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseWithoutComments {
    private Long id;
    private String newsText;
    private Long numOfComments;
}
