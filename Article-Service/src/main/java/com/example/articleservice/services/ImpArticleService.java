package com.example.articleservice.services;

import com.example.articleservice.beans.MemberBean;
import com.example.articleservice.entities.Article;
import com.example.articleservice.proxies.MemberProxy;
import com.example.articleservice.repositories.ArticleRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpArticleService implements IArticleService {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    MemberProxy memberProxy;

    @Override
    public Article addArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Long id) {
        this.articleRepository.deleteById(id);
    }

    @Override
    public Article updateArticle(Article article) {
        return articleRepository.saveAndFlush(article);
    }

    @Override
    public Article findArticleById(long id) {
        return this.articleRepository.findById(id).get();
    }

    @Override
    public Article findArticleByTitleMatchesRegex(String title) {
        return articleRepository.findArticleByTitleContainingIgnoreCase(title);
    }

    @Override
    public Article findArticleByTypeMatchesRegex(String type) {
        return articleRepository.findArticleByTypeContainingIgnoreCase(type);
    }

    @Override
    public List<Article> findAllArticles() {
        return this.articleRepository.findAll();
    }

    @Override
    public Article affectAuthorToArticle(Long idAuthor, Long idArticle) {
        Article article = findArticleById(idArticle);
        article.setAuthorId(idAuthor);
        return articleRepository.saveAndFlush(article);
    }

    @Override
    public List<Article> getAllArticlesByMember(Long idMember) {
        List<Article> articlesByMember = Lists.newArrayList();
        List<Article> articles = articleRepository.findAll();
        var articleList = articles.stream().filter(article -> article.getAuthorId().equals(idMember)).toList();
        articlesByMember.addAll(articleList);
        return articlesByMember;
    }
}
