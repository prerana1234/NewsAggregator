
package com.example.newsaggregator.controller;

import com.example.newsaggregator.model.AggregatedResponse;
import com.example.newsaggregator.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;
    public NewsController(NewsService newsService) { this.newsService = newsService; }

    @GetMapping
    public AggregatedResponse search(@RequestParam String keyword,
                                     @RequestParam(required = false) String city,
                                     @RequestParam(required = false) Integer pageNo,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false, defaultValue = "false") boolean offline) {
        return newsService.search(keyword, city, pageNo, pageSize, offline);
    }
}
