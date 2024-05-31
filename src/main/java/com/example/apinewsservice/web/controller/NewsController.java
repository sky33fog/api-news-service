package com.example.apinewsservice.web.controller;

import com.example.apinewsservice.aop.CheckingUser;
import com.example.apinewsservice.mapper.NewsMapper;
import com.example.apinewsservice.model.News;
import com.example.apinewsservice.service.NewsService;
import com.example.apinewsservice.web.model.NewsCreateRequest;
import com.example.apinewsservice.web.model.NewsFilter;
import com.example.apinewsservice.web.model.NewsOneResponse;
import com.example.apinewsservice.web.model.NewsSomeResponseList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    private final NewsMapper newsMapper;

    @GetMapping
    public ResponseEntity<NewsSomeResponseList> findAll(@Valid NewsFilter filter) {
        return ResponseEntity.ok(newsMapper.newsListToNewsSomeResponseLIst(newsService.findAll(filter)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsOneResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(newsMapper.newsToNewsOneResponse(newsService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<NewsOneResponse> create(@RequestBody @Valid NewsCreateRequest request) {
        News newNews = newsService.save(newsMapper.requestToNews(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.newsToNewsOneResponse(newNews));
    }

    @CheckingUser
    @PutMapping("/{userId}/{newsId}")
    public ResponseEntity<NewsOneResponse> update(@PathVariable Long newsId,
                                                  @RequestBody @Valid NewsCreateRequest request) {
        News updatedNews = newsService.update(newsMapper.requestToNews(newsId,request));
        return ResponseEntity.ok(newsMapper.newsToNewsOneResponse(updatedNews));
    }

    @CheckingUser
    @DeleteMapping("/{userId}/{newsId}")
    public ResponseEntity<Void> delete(@PathVariable Long newsId) {
        newsService.deleteById(newsId);
        return ResponseEntity.noContent().build();
    }
}
