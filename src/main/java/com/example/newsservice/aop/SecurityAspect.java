package com.example.newsservice.aop;

import com.example.newsservice.exception.IncorrectUserException;
import com.example.newsservice.model.News;
import com.example.newsservice.model.User;
import com.example.newsservice.model.Comment;
import com.example.newsservice.service.CommentService;
import com.example.newsservice.service.NewsService;
import com.example.newsservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
public class SecurityAspect {

    private final NewsService newsService;
    private final UserService userService;
    private final CommentService commentService;

//    @Before("@annotation(Editable)")
//    public void logBefore() {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//        News editedNews = newsService.findById(Long.valueOf(pathVariables.get("id")));
//        Long userId = Long.valueOf(request.getParameter("UserId"));
//        if(!Objects.equals(editedNews.getUser().getId(), userId)){
//            throw new IncorrectUserException(MessageFormat.format("Пользователь с ID {0} не может редактировать эту запись!", userId));
//        }
//    }

    @Before("@annotation(secured) && args(resourceId,..)")
    public void checkSecurity(JoinPoint joinPoint, Secured secured, Long resourceId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Пользователь не прошел аутентификацию");
        }

        Object principal = authentication.getPrincipal();
        String currentUsername;
        if (principal instanceof UserDetails) {
            currentUsername = ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            currentUsername = (String) principal;
        } else {
            throw new SecurityException("Unsupported principal type: " + principal.getClass().getName());
        }

        if (secured.roles().length > 0) {
            checkRoles(currentUsername, secured.roles());
        }

        if (secured.checkOwnership()) {
            checkOwnership(joinPoint, resourceId, currentUsername);
        }
    }

    private void checkRoles(String username, String[] roles) {

    }

    private void checkOwnership(JoinPoint joinPoint, Long resourceId, String currentUsername) {
        String resourceName = joinPoint.getSignature().getDeclaringTypeName();
        if (resourceName.contains("News")) {
            checkNewsOwnership(resourceId, currentUsername);
        } else if (resourceName.contains("Comment")) {
            checkCommentOwnership(resourceId, currentUsername);
        } else {
            throw new SecurityException("Unsupported resource type for ownership check");
        }
    }

    private void checkNewsOwnership(Long newsId, String currentUsername) {
        News news = newsService.findById(newsId);
        if (!news.getUser().getUsername().equals(currentUsername)) {
            throw new IncorrectUserException("User is not the owner of this news");
        }
    }

    private void checkCommentOwnership(Long commentId, String currentUsername) {
        Comment comment = commentService.findById(commentId);
        if (!comment.getUser().getUsername().equals(currentUsername)) {
            throw new IncorrectUserException("User is not the owner of this comment");
        }
    }

}
