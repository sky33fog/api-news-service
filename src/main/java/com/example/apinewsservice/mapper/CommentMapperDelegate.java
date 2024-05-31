package com.example.apinewsservice.mapper;

import com.example.apinewsservice.model.Comment;
import com.example.apinewsservice.service.NewsService;
import com.example.apinewsservice.service.UserService;
import com.example.apinewsservice.web.model.CommentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;

    @Override
    public Comment requestToComment(CommentCreateRequest request) {
        Comment newComment = new Comment();
        newComment.setText(request.getText());
        newComment.setUser(userService.findById(request.getUserId()));
        newComment.setNews(newsService.findById(request.getNewsId()));
        return newComment;
    }

    @Override
    public Comment requestToComment(Long id, CommentCreateRequest request) {
        Comment newComment = requestToComment(request);
        newComment.setId(id);
        return newComment;
    }
}
