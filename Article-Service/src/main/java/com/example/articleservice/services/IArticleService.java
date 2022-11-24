package com.example.articleservice.services;

import com.example.articleservice.entities.Article;

import java.util.List;

public interface IArticleService {
    Article addArticle(Article article);
    void deleteArticle(Long id);
    Article updateArticle(Article article);
    Article findArticleById(long id);
    Article findArticleByTitleMatchesRegex(String title);
    Article findArticleByTypeMatchesRegex(String type);
    List<Article> findAllArticles();
    Article affectAuthorToArticle(Long idAuthor , Long idArticle );
    List<Article> getAllArticlesByMember(Long idMember);
}
