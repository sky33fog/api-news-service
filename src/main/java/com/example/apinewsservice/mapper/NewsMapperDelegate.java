package com.example.apinewsservice.mapper;

import com.example.apinewsservice.model.News;
import com.example.apinewsservice.service.CategoryService;
import com.example.apinewsservice.service.UserService;
import com.example.apinewsservice.web.model.NewsCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public News requestToNews(NewsCreateRequest request) {
        News newNews = new News();

        newNews.setTitle(request.getTitle());
        newNews.setDescription(request.getDescription());
        newNews.setUser(userService.findById(request.getUserId()));
        newNews.setCategory(categoryService.findById(request.getCategoryId()));
        return newNews;
    }

    @Override
    public News requestToNews(Long id, NewsCreateRequest request) {
        News newNews = requestToNews(request);
        newNews.setId(id);
        return newNews;
    }
}
