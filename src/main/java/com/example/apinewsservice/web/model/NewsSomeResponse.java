package com.example.apinewsservice.web.model;

import lombok.Data;

@Data
public class NewsSomeResponse {

    private Long id;

    private String title;

    private String description;

    private String createAt;

    private Integer amountComments;
}
