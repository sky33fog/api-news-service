package com.example.apinewsservice.mapper;

import com.example.apinewsservice.model.Comment;
import com.example.apinewsservice.web.model.CommentCreateRequest;
import com.example.apinewsservice.web.model.CommentResponse;
import com.example.apinewsservice.web.model.CommentResponseList;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment requestToComment(CommentCreateRequest request);

    @Mapping(source = "commentId", target = "id")
    Comment requestToComment(Long commentId, CommentCreateRequest request);

    CommentResponse commentToResponse(Comment comment);

    default CommentResponseList commentListToCommentResponseList(List<Comment> comments) {
        CommentResponseList response = new CommentResponseList();

        response.setComments(comments.stream()
                .map(this::commentToResponse).collect(Collectors.toList()));
        return  response;
    }
}
