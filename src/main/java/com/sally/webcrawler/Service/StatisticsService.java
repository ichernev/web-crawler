package com.sally.webcrawler.Service;

import com.sally.webcrawler.model.Keyword;
import com.sally.webcrawler.model.MyUser;
import com.sally.webcrawler.model.News;
import com.sally.webcrawler.repository.KeywordRepository;
import com.sally.webcrawler.repository.MyUserRepository;
import com.sally.webcrawler.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {
    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private MyUserRepository myUserRepository;

    // Method to increment keyword counts
    public void trackKeywordSearch(String keyword) {
        Optional<Keyword> optionalKeyword = keywordRepository.findByName(keyword);
        if (optionalKeyword.isPresent()) {
            Keyword existingKeyword = optionalKeyword.get();
            existingKeyword.setSearchCount(existingKeyword.getSearchCount() + 1);
            keywordRepository.save(existingKeyword);
        } else {
            Keyword newKeyword = new Keyword();
            newKeyword.setName(keyword);
            newKeyword.setSearchCount(1);
            keywordRepository.save(newKeyword);
        }
    }

    // Method to retrieve top 3 searched keywords
    public List<Keyword> getTop3SearchedKeywords() {
        return keywordRepository.findTop3ByOrderBySearchCountDesc();
    }

    // Method to retrieve user with most searches
    public MyUser getUserWithMostSearches() {
        List<MyUser> users = myUserRepository.findAll();
        MyUser userWithMostSearches = null;
        int maxSearchCount = 0;

        for (MyUser user : users) {
            int userSearchCount = user.getSearchCount();
            if (userSearchCount > maxSearchCount) {
                maxSearchCount = userSearchCount;
                userWithMostSearches = user;
            }

        }
        return userWithMostSearches;
    }
    // Method to count keywords in news titles
    @Scheduled(cron = "0 0 0 * * ?") // Run daily at 12:00
    public void updateDailyKeywordCounts() {
        List<News> allNews = newsRepository.findAll();
        Map<String, Integer> keywordCountMap = new HashMap<>();

        for (News news : allNews) {
            String title = news.getTitle();
            for (String keyword : keywordRepository.findAll().stream().map(Keyword::getName).collect(Collectors.toList())) {
                if (title.contains(keyword)) {
                    keywordCountMap.put(keyword, keywordCountMap.getOrDefault(keyword, 0) + 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : keywordCountMap.entrySet()) {
            String keywordName = entry.getKey();
            int count = entry.getValue();
            Optional<Keyword> optionalKeyword = keywordRepository.findByName(keywordName);
            if (optionalKeyword.isPresent()) {
                Keyword existingKeyword = optionalKeyword.get();
                existingKeyword.setDailyCount(count);
                keywordRepository.save(existingKeyword);
            }
        }
    }
    // Method to retrieve users who searched the same keyword
    public List<MyUser> getUsersWithSameKeyword(String keyword, Long userId) {
        Optional<MyUser> userOptional = myUserRepository.findById(userId);
        if (userOptional.isPresent()) {
            MyUser originalUser = userOptional.get();

            Optional<Keyword> keywordOptional = keywordRepository.findByName(keyword);
            if (keywordOptional.isPresent()) {
                Keyword searchKeyword = keywordOptional.get();

                List<MyUser> usersWithSameKeyword = myUserRepository.findByKeywords(searchKeyword);
                usersWithSameKeyword.remove(originalUser);

                // Return at most 3 users
                return usersWithSameKeyword.stream().limit(3).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
