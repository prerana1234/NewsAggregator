package com.example.newsaggregator.service;

import com.example.newsaggregator.client.GuardianClient;
import com.example.newsaggregator.client.NytClient;
import com.example.newsaggregator.model.AggregatedResponse;
import com.example.newsaggregator.model.NewsArticle;
import com.example.newsaggregator.util.OfflineCache;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final GuardianClient guardianClient;
    private final NytClient nytClient;
    private final OfflineCache cache;

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;

    public NewsServiceImpl(GuardianClient guardianClient, NytClient nytClient, OfflineCache cache) {
        this.guardianClient = guardianClient;
        this.nytClient = nytClient;
        this.cache = cache;
    }

    @Override
    public AggregatedResponse search(String keyword, String city, Integer pageNo, Integer pageSize, boolean offlineMode) {
        long start = System.currentTimeMillis();

        // handle nulls safely
        int page = (pageNo == null || pageNo < 1) ? DEFAULT_PAGE : pageNo;
        int size = (pageSize == null || pageSize < 1) ? DEFAULT_SIZE : pageSize;

        List<NewsArticle> results = new ArrayList<>();

        if (offlineMode) {
            // offline mode – use cache only
            results = cache.get(keyword, page, size);
        } else {
            // fetch from both sources
            List<NewsArticle> guardianResults = guardianClient.search(keyword, page, size);
            List<NewsArticle> nytResults = nytClient.search(keyword, page, size);

            // aggregate and remove duplicates (by URL)
            Set<NewsArticle> unique = new LinkedHashSet<>();
            if (guardianResults != null) unique.addAll(guardianResults);
            if (nytResults != null) unique.addAll(nytResults);

            results = new ArrayList<>(unique);

            // fallback to cache if empty
            if (results.isEmpty()) {
                results = cache.get(keyword, page, size);
            }

            // update cache with current results
            if (!results.isEmpty()) {
                cache.put(keyword, page, size, results);
            }
        }

        // Calculate total pages safely
        int totalPages = (results.size() + size - 1) / size;
        if (totalPages < 1) totalPages = 1; // ensure minimum one page

        // Build response
        AggregatedResponse resp = new AggregatedResponse();
        resp.setKeyword(keyword);
        resp.setCity(city);
        resp.setArticles(results.stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .collect(Collectors.toList()));
        resp.setPageNo(page);
        resp.setPageSize(size);
        resp.setTotalPages(totalPages);
        resp.setTimeTakenMillis(System.currentTimeMillis() - start);

        // Pagination logic (safe)
        resp.setPrevPage(page > 1 ? page - 1 : null);
        resp.setNextPage(page < totalPages ? page + 1 : null);

        // Simple HATEOAS links
        resp.setSelf(String.format("/api/news?keyword=%s&pageNo=%d&pageSize=%d", keyword, page, size));
        if (resp.getNextPage() != null) {
            resp.setNext(String.format("/api/news?keyword=%s&pageNo=%d&pageSize=%d", keyword, resp.getNextPage(), size));
        }
        if (resp.getPrevPage() != null) {
            resp.setPrev(String.format("/api/news?keyword=%s&pageNo=%d&pageSize=%d", keyword, resp.getPrevPage(), size));
        }

        return resp;
    }
}
