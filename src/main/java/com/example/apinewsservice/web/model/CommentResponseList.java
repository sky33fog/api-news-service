package com.example.apinewsservice.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentResponseList {

    private List<CommentResponse> comments = new ArrayList<>();
}