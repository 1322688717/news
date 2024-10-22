package com.ice.news.model;

public class News {

    private String title;
    private String subtitle;
    private String details;
    private String newsType;
    private int imageResId; // 这里可以使用资源ID或图片URL

    public News(String title, String subtitle, String details, String newsType, int imageResId) {
        this.title = title;
        this.subtitle = subtitle;
        this.details = details;
        this.newsType = newsType;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDetail() {
        return details;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getNewsType() {
        return newsType;
    }
}
