package com.example.apinewsservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String name;

    private String password;

    private List<RoleResponse> roles;

    private List<NewsSomeResponse> newsList;

    private List<CommentResponse> commentList;
}
