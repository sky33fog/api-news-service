package com.example.apinewsservice.repository;

import com.example.apinewsservice.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

    Page<News> findAllByCategory(String category, Pageable pageable);
    Page<News> findAllByUser(String author, Pageable pageable);
}
