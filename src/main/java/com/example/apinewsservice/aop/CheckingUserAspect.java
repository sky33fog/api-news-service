package com.example.apinewsservice.aop;

import com.example.apinewsservice.exception.UnauthorizedUserException;
import com.example.apinewsservice.model.Comment;
import com.example.apinewsservice.model.News;
import com.example.apinewsservice.service.CommentService;
import com.example.apinewsservice.service.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.text.MessageFormat;
import java.util.Map;

@Aspect
@Component
public class CheckingUserAspect {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentService commentService;

    @Before("@annotation(CheckingUser)")
    public void checkUser(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Long entityUserId = 0L;
        if(pathVariables.containsKey("newsId")) {
            News news = newsService.findById(Long.parseLong(pathVariables.get("newsId")));
            entityUserId = news.getUser().getId();
        } else if (pathVariables.containsKey("commentId")) {
            Comment comment = commentService.findById(Long.parseLong(pathVariables.get("commentId")));
            entityUserId = comment.getUser().getId();
        }

        if(Long.parseLong(pathVariables.get("userId")) != entityUserId) {
            throw new UnauthorizedUserException(MessageFormat.format("Пользователь с ID {0} не имеет прав на изменение данной cущности",
                    pathVariables.get("userId")));
        }
    }
}
