package com.example.apinewsservice.mapper;

import com.example.apinewsservice.model.News;
import com.example.apinewsservice.web.model.NewsCreateRequest;
import com.example.apinewsservice.web.model.NewsOneResponse;
import com.example.apinewsservice.web.model.NewsSomeResponse;
import com.example.apinewsservice.web.model.NewsSomeResponseList;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;


@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {

    News requestToNews(NewsCreateRequest request);

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, NewsCreateRequest request);

    NewsOneResponse newsToNewsOneResponse(News news);

    default NewsSomeResponse newsToNewsSomeResponse(News news) {
        NewsSomeResponse response = new NewsSomeResponse();
        response.setId(news.getId());
        response.setTitle(news.getTitle());
        response.setDescription(news.getDescription());
        response.setAmountComments(news.getCommentList().size());
        response.setCreateAt(news.getCreateAt().toString());
        return response;
    }

    default NewsSomeResponseList newsListToNewsSomeResponseLIst(List<News> newsList) {
        NewsSomeResponseList responseList = new NewsSomeResponseList();

        responseList.setNewsList(newsList.stream()
                .map(this::newsToNewsSomeResponse).collect(Collectors.toList()));
        return responseList;
    }
}
