
package com.example.newsaggregator.model;

import java.time.Instant;
import java.util.Objects;

public class NewsArticle {
    private String id;
    private String title;
    private String description;
    private String url;
    private String source;
    private Instant publishedAt;

    public NewsArticle() {}

    public NewsArticle(String id, String title, String description, String url, String source, Instant publishedAt) {
        this.id = id; this.title = title; this.description = description; this.url = url; this.source = source; this.publishedAt = publishedAt;
    }

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public Instant getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsArticle)) return false;
        NewsArticle that = (NewsArticle) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() { return Objects.hash(url); }
}
