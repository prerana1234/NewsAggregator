
package com.example.newsaggregator.model;

import java.util.List;

public class AggregatedResponse {
    private String keyword;
    private String city;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private long timeTakenMillis;
    private List<NewsArticle> articles;
    private Integer prevPage;
    private Integer nextPage;
    private String self;
    private String next;
    private String prev;

    // getters and setters
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public int getPageNo() { return pageNo; }
    public void setPageNo(int pageNo) { this.pageNo = pageNo; }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    public long getTimeTakenMillis() { return timeTakenMillis; }
    public void setTimeTakenMillis(long timeTakenMillis) { this.timeTakenMillis = timeTakenMillis; }
    public List<NewsArticle> getArticles() { return articles; }
    public void setArticles(List<NewsArticle> articles) { this.articles = articles; }
    public Integer getPrevPage() { return prevPage; }
    public void setPrevPage(Integer prevPage) { this.prevPage = prevPage; }
    public Integer getNextPage() { return nextPage; }
    public void setNextPage(Integer nextPage) { this.nextPage = nextPage; }
    public String getSelf() { return self; }
    public void setSelf(String self) { this.self = self; }
    public String getNext() { return next; }
    public void setNext(String next) { this.next = next; }
    public String getPrev() { return prev; }
    public void setPrev(String prev) { this.prev = prev; }
}
