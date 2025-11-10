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
public class GuardianClient {

    @Value("${guardian.api.key:}")
    private String apiKey;

    private static final String BASE_URL = "https://content.guardianapis.com/search";

    public List<NewsArticle> search(String keyword, int page, int pageSize) {
        List<NewsArticle> articles = new ArrayList<>();

        try {
            if (apiKey == null || apiKey.isEmpty()) {
                // fallback offline
                for (int i = 1; i <= pageSize; i++) {
                    articles.add(new NewsArticle(
                            "guardian-" + i,
                            "Offline Guardian Headline " + i + " for " + keyword,
                            "Sample Guardian description",
                            "https://theguardian.com/sample/" + i,
                            "Guardian",
                            Instant.now()
                    ));
                }
                return articles;
            }

            String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                    .queryParam("api-key", apiKey)
                    .queryParam("q", keyword)
                    .queryParam("page", page)
                    .queryParam("page-size", pageSize)
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getBody() != null && response.getBody().containsKey("response")) {
                Map<String, Object> resp = (Map<String, Object>) response.getBody().get("response");
                List<Map<String, Object>> results = (List<Map<String, Object>>) resp.get("results");
                if (results != null) {
                    for (Map<String, Object> item : results) {
                        String id = (String) item.get("id");
                        String title = (String) item.get("webTitle");
                        String webUrl = (String) item.get("webUrl");
                        String section = (String) item.get("sectionName");
                        articles.add(new NewsArticle(
                                id, title, section, webUrl, "Guardian", Instant.now()
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