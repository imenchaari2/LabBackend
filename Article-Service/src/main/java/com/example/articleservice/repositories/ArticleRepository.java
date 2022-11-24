package com.example.articleservice.repositories;

import com.example.articleservice.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findArticleByTitleContainingIgnoreCase(String title);
    Article findArticleByTypeContainingIgnoreCase(String type);
}
