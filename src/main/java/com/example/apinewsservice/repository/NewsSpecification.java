package com.example.apinewsservice.repository;

import com.example.apinewsservice.model.News;
import com.example.apinewsservice.web.model.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {

    static Specification<News> withFilter(NewsFilter filter) {
        return Specification.where(byCategoryName(filter.getCategory()))
                .and(byAuthorName(filter.getAuthor()));
    }

    static Specification<News> byCategoryName(String categoryName) {

        return (root, query, criteriaBuilder) -> {
            if(categoryName == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("category").get("rubric"), categoryName);
        };
    }

    static Specification<News> byAuthorName(String authorName) {
        return (root, query, criteriaBuilder) -> {
            if (authorName == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("user").get("name"), authorName);
        };
    }
}
