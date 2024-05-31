package com.example.apinewsservice.web.model;

import lombok.Data;

import java.util.List;

@Data
public class NewsOneResponse {

    private Long id;

    private String title;

    private String description;

    private String createAt;

    private List<CommentResponse> commentList;

}
