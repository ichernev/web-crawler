package com.sally.webcrawler.controller;

import com.sally.webcrawler.model.News;
import com.sally.webcrawler.repository.NewsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping
public class SiteController {

    private final NewsRepository newsRepository;

    public SiteController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("/")
    public String home() {

//        List<News> extractedNews;
//
//        for (int i = 1; i <= 31; i++) {
//            System.out.println("Extracting for date 2024-1-" + i + " ... ");
//
//            extractedNews = extractNews("https://www.novinite.bg/archives/2024-1-" + i);
//
//            List<News> s = newsRepository.saveAll(extractedNews);
//
//            System.out.println("Extracted and saved total of " + s.size() + " news");
//        }
//
//        for (int i = 1; i <= 29; i++) {
//            System.out.print("Extracting for date 2024-2-" + i);
//
//            extractedNews = extractNews("https://www.novinite.bg/archives/2024-2-" + i);
//
//            List<News> s = newsRepository.saveAll(extractedNews);
//
//            System.out.println(" [ Extracted and saved total of " + s.size() + " news ]");
//        }

        return "index";
    }

    public static List<News> extractNews(String pageUrl) {
        List<News> news = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(pageUrl).get();
            Elements newsCards = doc.select(".card");
            for (Element newsCard : newsCards) {
                String datetimestring = newsCard.select(".datetime").text();

                String title = newsCard.select(".card-body h2").text();

                String url = newsCard.select(".news-list-article-img").attr("href");

                News newsToAdd = new News();

                newsToAdd.setTitle(title);
                newsToAdd.setUrl(url);
                newsToAdd.setDateTimeAdded(parseDateTime(datetimestring));

                news.add(newsToAdd);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle connection or parsing errors
        }
        return news;
    }

    public static LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                // Define the pattern for parsing the time (e.g., 19:45)
                .appendPattern("HH:mm")
                // Define a literal for the space between time and date
                .appendLiteral(" ")
                // Define the pattern for parsing the date (e.g., 27.03.2024)
                .appendPattern("dd.MM.yyyy")
                // Define the default value for missing time (midnight)
                .parseDefaulting(java.time.temporal.ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(java.time.temporal.ChronoField.MINUTE_OF_HOUR, 0)
                // Build the formatter
                .toFormatter(Locale.ENGLISH);

        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
