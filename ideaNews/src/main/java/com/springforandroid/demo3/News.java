package com.springforandroid.demo3;

import jakarta.persistence.*;

@Entity // 标记为 JPA 实体
@Table(name = "news") // 可选，指定数据库表名
public class News {

    @Id // 标记为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增 ID
    private Long id;

    private String logo;
    private String title;
    private String subtitle;
    private String details;
    private String type; // 新增新闻类型字段

    // 无参构造函数
    public News() {}

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", logo='" + logo + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", details='" + details + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
