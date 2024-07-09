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
    private final CommentService commentService;

    @Before("@annotation(secured) && args(resourceId,..)")
    public void checkSecurity(JoinPoint joinPoint, Secured secured, Long resourceId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Пользователь не прошел аутентификацию");
        }

        boolean isAdminOrModerator = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN") || authority.getAuthority().equals("ROLE_MODERATOR"));

        Object principal = authentication.getPrincipal();
        String currentUsername;
        if (principal instanceof UserDetails) {
            currentUsername = ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            currentUsername = (String) principal;
        } else {
            throw new SecurityException("Неподдерживаемый тип аутентификации: " + principal.getClass().getName());
        }

        if (secured.roles().length > 0 && !isAdminOrModerator) {
            checkOwnership(joinPoint, resourceId, currentUsername);
        }

        if (secured.checkOwnership()) {
            checkOwnership(joinPoint, resourceId, currentUsername);
        }
    }


    private void checkOwnership(JoinPoint joinPoint, Long resourceId, String currentUsername) {
        String resourceName = joinPoint.getSignature().getDeclaringTypeName();
        if (resourceName.contains("News")) {
            checkNewsOwnership(resourceId, currentUsername);
        } else if (resourceName.contains("Comment")) {
            checkCommentOwnership(resourceId, currentUsername);
        } else {
            throw new SecurityException("Неподдерживаемый тип ресурса");
        }
    }

    private void checkNewsOwnership(Long newsId, String currentUsername) {
        News news = newsService.findById(newsId);
        if (!news.getUser().getUsername().equals(currentUsername)) {
            throw new IncorrectUserException("Пользователь не является владельцем этой новости");
        }
    }

    private void checkCommentOwnership(Long commentId, String currentUsername) {
        Comment comment = commentService.findById(commentId);
        if (!comment.getUser().getUsername().equals(currentUsername)) {
            throw new IncorrectUserException("Пользователь не является владельцем этого комментария");
        }
    }

}
