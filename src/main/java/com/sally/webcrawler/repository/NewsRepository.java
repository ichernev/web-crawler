package com.sally.webcrawler.repository;

import com.sally.webcrawler.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findByTitleContainingIgnoreCase(String keyword);
}
