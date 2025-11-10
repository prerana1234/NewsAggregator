
package com.example.newsaggregator.service;

import com.example.newsaggregator.model.AggregatedResponse;

public interface NewsService {
    AggregatedResponse search(String keyword, String city, Integer pageNo, Integer pageSize, boolean offlineMode);
}
