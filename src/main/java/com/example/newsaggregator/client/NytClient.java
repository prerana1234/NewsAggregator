
package com.example.newsaggregator.client;

import com.example.newsaggregator.model.NewsArticle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class NytClient {

    @Value("${nyt.api.key:}")
    private String apiKey;

    private static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

    public List<NewsArticle> search(String keyword, int page, int pageSize) {
        List<NewsArticle> articles = new ArrayList<>();

        try {
            if (apiKey == null || apiKey.isEmpty()) {
                // offline fallback for NYT
                for (int i = 1; i <= pageSize; i++) {
                    articles.add(new NewsArticle(
                            "nyt-" + i,
                            "Offline NYT Headline " + i + " for " + keyword,
                            "Sample NYT description",
                            "https://nytimes.com/sample/" + i,
                            "New York Times",
                            Instant.now()
                    ));
                }
                return articles;
            }

            String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                    .queryParam("q", keyword)
                    .queryParam("page", page - 1) // NYT pages start from 0
                    .queryParam("api-key", apiKey)
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getBody() != null && response.getBody().containsKey("response")) {
                Map<String, Object> resp = (Map<String, Object>) response.getBody().get("response");
                List<Map<String, Object>> docs = (List<Map<String, Object>>) resp.get("docs");

                if (docs != null) {
                    for (Map<String, Object> doc : docs) {
                        String id = (String) doc.get("_id");
                        Map<String, Object> headline = (Map<String, Object>) doc.get("headline");
                        String title = headline != null ? (String) headline.get("main") : "No title";
                        String webUrl = (String) doc.get("web_url");
                        articles.add(new NewsArticle(
                                id,
                                title,
                                "", // description not always provided
                                webUrl,
                                "New York Times",
                                Instant.now()
                        ));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }
}