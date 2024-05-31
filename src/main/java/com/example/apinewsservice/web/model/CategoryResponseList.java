package com.example.apinewsservice.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryResponseList {

    private List<CategoryResponse> categories = new ArrayList<>();
}
