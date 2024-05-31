package com.example.apinewsservice.service;

import com.example.apinewsservice.exception.EntityNotFoundException;
import com.example.apinewsservice.model.News;
import com.example.apinewsservice.repository.NewsRepository;
import com.example.apinewsservice.repository.NewsSpecification;
import com.example.apinewsservice.util.BeanUtils;
import com.example.apinewsservice.web.model.NewsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public List<News> findAll(NewsFilter filter) {
                return newsRepository.findAll(NewsSpecification.withFilter(filter),
                        PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    public News findById(Long id) {
        return newsRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Новость с id {0}, не найдена", id)));
    }

    public News save(News news) {
        return newsRepository.save(news);
    }

    public News update(News news) {
        News existedNews = findById(news.getId());
        BeanUtils.copyNonNullProperties(news, existedNews);
        return newsRepository.save(existedNews);
    }

    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}
