package com.sally.webcrawler.model;

import jakarta.persistence.*;
import lombok.Data;
// import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "statistics")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String topSearchedKeyword1;
    private String topSearchedKeyword2;
    private String topSearchedKeyword3;

    // User with the most searches
    private Long userId;

    // Daily count of keywords found in news titles
    private int dailyKeywordCount;

}
