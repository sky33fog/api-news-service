package com.example.apinewsservice.repository;

import com.example.apinewsservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM com.example.apinewsservice.model.Comment c WHERE c.news.id =:newsId")
    public List<Comment> findAllByNewsId(Long newsId);
}
