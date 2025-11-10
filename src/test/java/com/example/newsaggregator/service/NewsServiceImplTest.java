
package com.example.newsaggregator.service;

import com.example.newsaggregator.client.GuardianClient;
import com.example.newsaggregator.client.NytClient;
import com.example.newsaggregator.model.AggregatedResponse;
import com.example.newsaggregator.model.NewsArticle;
import com.example.newsaggregator.util.OfflineCache;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NewsServiceImplTest {

    @Test
    void aggregatesRemoveDuplicates() {
        GuardianClient g = mock(GuardianClient.class);
        NytClient n = mock(NytClient.class);
        OfflineCache cache = new OfflineCache();

        NewsArticle a = new NewsArticle();
        a.setUrl("http://example.com/1");
        a.setTitle("Title 1");

        NewsArticle b = new NewsArticle();
        b.setUrl("http://example.com/1");
        b.setTitle("Title 1 NYT");

        when(g.search("apple",1,10)).thenReturn(List.of(a));
        when(n.search("apple",1,10)).thenReturn(List.of(b));

        NewsServiceImpl svc = new NewsServiceImpl(g, n, cache);
        AggregatedResponse r = svc.search("apple", null, 1, 10, false);
        assertEquals(1, r.getArticles().size());
    }
}
