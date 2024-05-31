package com.example.apinewsservice.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsSomeResponseList {

    private List<NewsSomeResponse> newsList = new ArrayList<>();
}
