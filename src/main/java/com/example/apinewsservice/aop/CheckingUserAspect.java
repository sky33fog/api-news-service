package com.example.apinewsservice.aop;

import com.example.apinewsservice.exception.UnauthorizedUserException;
import com.example.apinewsservice.model.*;
import com.example.apinewsservice.security.AppUserPrincipal;
import com.example.apinewsservice.service.CommentService;
import com.example.apinewsservice.service.NewsService;
import com.example.apinewsservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.text.MessageFormat;
import java.util.*;

@Aspect
@Component
public class CheckingUserAspect {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Before("@annotation(CheckingUser)")
    public void checkUser(JoinPoint joinPoint) {
        Map<String, String> pathVariables = getPathVariables();
        User currentUser = userService.findByUserName(getAppUserPrincipal().getUsername());
        List<String> userRoles = currentUser.getRoles().stream().map(Role::getAuthority).map(RoleType::toString).toList();

        if(userRoles.contains("ROLE_ADMIN") || userRoles.contains("ROLE_MODERATOR")) {
            return;
        }

        if(!pathVariables.get("id").equals(currentUser.getId().toString())) {
            throw new UnauthorizedUserException(MessageFormat.format("Пользователь с ID {0} не имеет прав на запрос данной cущности",
                    pathVariables.get("id")));
        }
    }

    @Before("@annotation(CheckingUpdateNews)")
    public void checkUpdateNews(JoinPoint joinPoint) {
        News newsForUpdate = newsService.findById(Long.parseLong(getPathVariables().get("newsId")));
        User requestingUser = userService.findByUserName(getAppUserPrincipal().getUsername());
        if(!Objects.equals(newsForUpdate.getUser().getId(), requestingUser.getId())) {
            throw new UnauthorizedUserException(MessageFormat.format("Пользователь с ID {0} не имеет прав на обновление данной новости",
                    requestingUser.getId()));
        }
    }

    @Before("@annotation(CheckingDeleteNews)")
    public void checkDeleteNews(JoinPoint joinPoint) {
        News newsForDelete = newsService.findById(Long.parseLong(getPathVariables().get("newsId")));
        User requestingUser = userService.findByUserName(getAppUserPrincipal().getUsername());
        List<String> userRoles = requestingUser.getRoles().stream().map(Role::getAuthority).map(RoleType::toString).toList();

        if(userRoles.contains("ROLE_ADMIN") || userRoles.contains("ROLE_MODERATOR")) {
            return;
        }

        if(!Objects.equals(newsForDelete.getUser().getId(), requestingUser.getId())) {
            throw new UnauthorizedUserException(MessageFormat.format("Пользователь с ID {0} не имеет прав на удаление данной новости",
                    requestingUser.getId()));
        }
    }

    @Before("@annotation(CheckingUpdateComment)")
    public void checkUpdateComment(JoinPoint joinPoint) {
        Comment commentForUpdate = commentService.findById(Long.parseLong(getPathVariables().get("commentId")));
        User requestingUser = userService.findByUserName(getAppUserPrincipal().getUsername());

        if(!Objects.equals(commentForUpdate.getUser().getId(), requestingUser.getId())) {
            throw new UnauthorizedUserException(MessageFormat.format("Пользователь с ID {0} не имеет прав на изменение данного комментария",
                    requestingUser.getId()));
        }
    }

    @Before("@annotation(CheckingDeleteComment)")
    public void checkingDeleteComment(JoinPoint joinPoint) {
        Comment commentForUpdate = commentService.findById(Long.parseLong(getPathVariables().get("commentId")));
        User requestingUser = userService.findByUserName(getAppUserPrincipal().getUsername());
        List<String> userRoles = requestingUser.getRoles().stream().map(Role::getAuthority).map(RoleType::toString).toList();

        if(userRoles.contains("ROLE_ADMIN") || userRoles.contains("ROLE_MODERATOR")) {
            return;
        }
        if(!Objects.equals(commentForUpdate.getUser().getId(), requestingUser.getId())) {
            throw new UnauthorizedUserException(MessageFormat.format("Пользователь с ID {0} не имеет прав на удаление данного комментария",
                    requestingUser.getId()));
        }

    }

    private static AppUserPrincipal getAppUserPrincipal() {
        return (AppUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private static Map<String, String> getPathVariables() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        return  (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    }
}