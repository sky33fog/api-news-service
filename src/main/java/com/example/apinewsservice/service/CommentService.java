package com.example.apinewsservice.service;

import com.example.apinewsservice.exception.EntityNotFoundException;
import com.example.apinewsservice.model.Comment;
import com.example.apinewsservice.model.News;
import com.example.apinewsservice.repository.CommentRepository;
import com.example.apinewsservice.repository.NewsRepository;
import com.example.apinewsservice.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final NewsRepository newsRepository;

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Комментарий с id {0}, не найден.", id)));
    }

    public List<Comment> findAllByNewsId(Long newsId) {
        newsRepository.findById(newsId).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Новость с id {0}, не найдена.", newsId)));
        return commentRepository.findAllByNewsId(newsId);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment update(Comment comment) {
        Comment existedComment = findById(comment.getId());
        BeanUtils.copyNonNullProperties(comment, existedComment);
         return commentRepository.save(existedComment);
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
