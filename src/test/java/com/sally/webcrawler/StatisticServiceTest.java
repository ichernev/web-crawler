package com.sally.webcrawler;

import com.sally.webcrawler.Service.StatisticsService;
import com.sally.webcrawler.model.Keyword;
import com.sally.webcrawler.repository.KeywordRepository;
import com.sally.webcrawler.repository.MyUserRepository;
import com.sally.webcrawler.repository.NewsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class StatisticServiceTest {

    @Mock
    private KeywordRepository keywordRepository;

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private MyUserRepository myUserRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTrackKeywordSearch_ExistingKeyword() {
        // Mock behavior of keywordRepository
        String keyword = "testKeyword";
        Keyword existingKeyword = new Keyword();
        existingKeyword.setName(keyword);
        existingKeyword.setSearchCount(1);
        when(keywordRepository.findByName(keyword)).thenReturn(Optional.of(existingKeyword));

        // Call the method under test
        statisticsService.trackKeywordSearch(keyword);

        // Verify that save method is called on keywordRepository with updated count
        verify(keywordRepository, times(1)).save(existingKeyword);
        Assertions.assertEquals(2, existingKeyword.getSearchCount());
    }

    @Test
    public void testTrackKeywordSearch_NewKeyword() {
        // Mock behavior of keywordRepository
        String keyword = "newKeyword";
        when(keywordRepository.findByName(keyword)).thenReturn(Optional.empty());

        // Call the method under test
        statisticsService.trackKeywordSearch(keyword);

        // Verify that save method is called on keywordRepository with new keyword
        verify(keywordRepository, times(1)).save(argThat(k -> k.getName().equals(keyword) && k.getSearchCount() == 1));
    }
}
