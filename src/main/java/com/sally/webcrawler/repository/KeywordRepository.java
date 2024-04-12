package com.sally.webcrawler.repository;

import com.sally.webcrawler.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {
    Optional<Keyword> findByName(String name);
    List<Keyword> findTop3ByOrderBySearchCountDesc();
}
