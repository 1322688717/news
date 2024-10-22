package com.springforandroid.demo3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;



    public News createNews(News news) {
        return newsRepository.save(news);
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public News updateNews(Long id, News news) {
        if (newsRepository.existsById(id)) {
            news.setId(id);
            return newsRepository.save(news);
        }
        return null;
    }

    public News getNewsById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    // 根据类型获取新闻
    public List<News> getNewsByType(String type) {
        return newsRepository.findByType(type);
    }
}
