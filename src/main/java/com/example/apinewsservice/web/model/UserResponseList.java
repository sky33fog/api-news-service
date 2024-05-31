package com.example.apinewsservice.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponseList {

    private List<UserResponse> users = new ArrayList<>();
}
