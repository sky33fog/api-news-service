package com.example.apinewsservice.web.controller;

import com.example.apinewsservice.aop.CheckingDeleteNews;
import com.example.apinewsservice.aop.CheckingUpdateNews;
import com.example.apinewsservice.mapper.NewsMapper;
import com.example.apinewsservice.model.News;
import com.example.apinewsservice.security.AppUserPrincipal;
import com.example.apinewsservice.service.NewsService;
import com.example.apinewsservice.service.UserService;
import com.example.apinewsservice.web.model.NewsCreateRequest;
import com.example.apinewsservice.web.model.NewsFilter;
import com.example.apinewsservice.web.model.NewsOneResponse;
import com.example.apinewsservice.web.model.NewsSomeResponseList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    private final UserService userService;

    private final NewsMapper newsMapper;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsSomeResponseList> findAll(@Valid NewsFilter filter) {
        return ResponseEntity.ok(newsMapper.newsListToNewsSomeResponseLIst(newsService.findAll(filter)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsOneResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(newsMapper.newsToNewsOneResponse(newsService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsOneResponse> create(@RequestBody @Valid NewsCreateRequest request) {
        News newNews = newsMapper.requestToNews(request);
        var appUserPrincipal = (AppUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newNews.setUser(userService.findByUserName(appUserPrincipal.getUsername()));
        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.newsToNewsOneResponse(newsService.save(newNews)));
    }

    @PutMapping("/{newsId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @CheckingUpdateNews
    public ResponseEntity<NewsOneResponse> update(@PathVariable Long newsId,
                                                  @RequestBody @Valid NewsCreateRequest request) {
        News updatedNews = newsService.update(newsMapper.requestToNews(newsId,request));
        return ResponseEntity.ok(newsMapper.newsToNewsOneResponse(updatedNews));
    }


    @DeleteMapping("/{newsId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @CheckingDeleteNews
    public ResponseEntity<Void> delete(@PathVariable Long newsId) {
        newsService.deleteById(newsId);
        return ResponseEntity.noContent().build();
    }
}
