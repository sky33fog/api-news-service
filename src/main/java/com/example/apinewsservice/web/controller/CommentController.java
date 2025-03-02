package com.example.apinewsservice.web.controller;

import com.example.apinewsservice.aop.CheckingDeleteComment;
import com.example.apinewsservice.aop.CheckingUpdateComment;
import com.example.apinewsservice.aop.CheckingUser;
import com.example.apinewsservice.mapper.CommentMapper;
import com.example.apinewsservice.model.Comment;
import com.example.apinewsservice.security.AppUserPrincipal;
import com.example.apinewsservice.service.CommentService;
import com.example.apinewsservice.service.UserService;
import com.example.apinewsservice.web.model.CommentCreateRequest;
import com.example.apinewsservice.web.model.CommentResponse;
import com.example.apinewsservice.web.model.CommentResponseList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    private final UserService userService;

    @GetMapping("/{newsId}")
    public ResponseEntity<CommentResponseList> findAllByNewsId(@PathVariable Long newsId) {
        return ResponseEntity.ok(commentMapper.commentListToCommentResponseList(commentService.findAllByNewsId(newsId)));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid CommentCreateRequest request) {
        AppUserPrincipal appUserPrincipal = (AppUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment newComment = commentMapper.requestToComment(request);
        newComment.setUser(userService.findByUserName(appUserPrincipal.getUsername()));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToResponse(commentService.save(newComment)));
    }

    @CheckingUser
    @PutMapping("/{commentId}")
    @CheckingUpdateComment
    public ResponseEntity<CommentResponse> update(@PathVariable Long commentId,
                                                  @RequestBody @Valid CommentCreateRequest request) {
        Comment updatedComment = commentService.update(commentMapper.requestToComment(commentId, request));
        return ResponseEntity.ok(commentMapper.commentToResponse(updatedComment));
    }

    @CheckingUser
    @DeleteMapping("/{commentId}")
    @CheckingDeleteComment
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {
        commentService.deleteById(commentId);
        return ResponseEntity.noContent().build();
    }
}
