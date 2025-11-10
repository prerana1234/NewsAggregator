
package com.example.newsaggregator.util;

import com.example.newsaggregator.model.NewsArticle;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OfflineCache {
    private final Map<String, List<NewsArticle>> store = new LinkedHashMap<>();
    public List<NewsArticle> get(String keyword, int page, int size) {
        String k = key(keyword, page, size);
        return store.getOrDefault(k, Collections.emptyList());
    }
    public void put(String keyword, int page, int size, List<NewsArticle> articles) {
        String k = key(keyword, page, size);
        store.put(k, new ArrayList<>(articles));
        if (store.size() > 200) {
            Iterator<String> it = store.keySet().iterator();
            it.next(); it.remove();
        }
    }
    private String key(String keyword, int page, int size) { return keyword + "|" + page + "|" + size; }
}
