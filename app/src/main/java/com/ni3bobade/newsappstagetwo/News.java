package com.ni3bobade.newsappstagetwo;

public class News {
    private String newsSectionName;
    private String newsTitle;
    private String newsAuthorName;
    private String newsPublishedDate;
    private String newsWebUrl;

    // constructor

    public News(String newsSectionName, String newsTitle, String newsAuthorName, String newsPublishedDate, String newsWebUrl) {
        this.newsSectionName = newsSectionName;
        this.newsTitle = newsTitle;
        this.newsAuthorName = newsAuthorName;
        this.newsPublishedDate = newsPublishedDate;
        this.newsWebUrl = newsWebUrl;
    }

    // getters

    public String getNewsSectionName() {
        return newsSectionName;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsAuthorName() {
        return newsAuthorName;
    }

    public String getNewsPublishedDate() {
        return newsPublishedDate;
    }

    public String getNewsWebUrl() {
        return newsWebUrl;
    }
}

