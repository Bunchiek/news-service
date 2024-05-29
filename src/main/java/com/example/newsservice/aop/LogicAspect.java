package com.example.newsservice.aop;

import com.example.newsservice.exception.IncorrectUserException;
import com.example.newsservice.model.News;
import com.example.newsservice.service.NewsService;
import com.example.newsservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LogicAspect {

    private final NewsService newsService;

    @Before("@annotation(Editable)")
    public void logBefore() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        News editedNews = newsService.findById(Long.valueOf(pathVariables.get("id")));
        Long userId = Long.valueOf(request.getParameter("UserId"));
        if(!Objects.equals(editedNews.getUser().getId(), userId)){
            throw new IncorrectUserException(MessageFormat.format("Пользователь с ID {0} не может редактировать эту новость!", userId));
        }
    }

}
