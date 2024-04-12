package com.sally.webcrawler.repository;

import com.sally.webcrawler.model.Statistics;
import org.springframework.data.repository.CrudRepository;

public interface StatisticsRepository extends CrudRepository<Statistics,Long> {
}
