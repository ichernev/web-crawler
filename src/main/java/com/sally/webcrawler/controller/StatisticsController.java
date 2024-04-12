package com.sally.webcrawler.controller;

import com.sally.webcrawler.Service.StatisticsService;
import com.sally.webcrawler.model.Keyword;
import com.sally.webcrawler.model.MyUser;
import com.sally.webcrawler.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/statistics")

public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private KeywordRepository keywordRepository;

    @GetMapping("/top3keywords")
    public List<Keyword> getTop3SearchedKeywords() {
        return statisticsService.getTop3SearchedKeywords();
    }

    @GetMapping("/userwithmostsearches")
    public MyUser getUserWithMostSearches() {
        return statisticsService.getUserWithMostSearches();
    }

    @PostMapping("/updatedailykeywordcounts")
    public void updateDailyKeywordCounts() {
        statisticsService.updateDailyKeywordCounts();
    }

    @GetMapping("/usersWithSameKeyword/{keyword}/{userId}")
    public List<MyUser> getUsersWithSameKeyword(@PathVariable String keyword, @PathVariable Long userId) {
        return statisticsService.getUsersWithSameKeyword(keyword, userId);
    }
    @GetMapping("/keywords/{keyword}/count")
    public int getKeywordCount(String keyword) {
        Optional<Keyword> optionalKeyword = keywordRepository.findByName(keyword);
        return optionalKeyword.map(Keyword::getSearchCount).orElse(0);
    }
    @GetMapping("/keywords/{keyword}/dailycount")
    public int getKeywordDailyCount(String keyword) {
        Optional<Keyword> optionalKeyword = keywordRepository.findByName(keyword);
        return optionalKeyword.map(Keyword::getDailyCount).orElse(0);
    }
}
