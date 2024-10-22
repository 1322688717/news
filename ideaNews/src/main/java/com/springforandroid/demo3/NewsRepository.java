package com.springforandroid.demo3;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface NewsRepository extends JpaRepository<News, Long> {
    // 根据类型查找新闻
    List<News> findByType(String type);
}
