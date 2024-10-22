package com.springforandroid.demo3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    // 添加新闻
    @PostMapping("/add")
    public News addNews(
            @RequestParam String logo,
            @RequestParam String title,
            @RequestParam String subtitle,
            @RequestParam String details,
            @RequestParam String type) {

        News news = new News();
        news.setLogo(logo);
        news.setTitle(title);
        news.setSubtitle(subtitle);
        news.setDetails(details);
        news.setType(type);

        return newsService.createNews(news);
    }

    // 删除新闻
    @DeleteMapping("/delete/{id}")
    public String deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return "News deleted successfully!";
    }

    // 获取所有新闻
    @GetMapping("/all")
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    // 更新新闻
    @PutMapping("/update/{id}")
    public News updateNews(@PathVariable Long id,
                           @RequestParam String logo,
                           @RequestParam String title,
                           @RequestParam String subtitle,
                           @RequestParam String details,
                           @RequestParam String type) {

        News news = new News();
        news.setId(id);
        news.setLogo(logo);
        news.setTitle(title);
        news.setSubtitle(subtitle);
        news.setDetails(details);
        news.setType(type);

        return newsService.updateNews(id, news);
    }

    // 根据 ID 获取新闻
    @GetMapping("/{id}")
    public News getNewsById(@PathVariable Long id) {
        return newsService.getNewsById(id);
    }


    // 查询新闻类型
    @PostMapping("/type")
    public List<News> getNewsByType(@RequestBody Map<String, String> requestBody) {
        String type = requestBody.get("type");
        logger.debug("Fetching news by type: {}", type);
        return newsService.getNewsByType(type);
    }
}
