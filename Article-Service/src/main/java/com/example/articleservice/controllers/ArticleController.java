package com.example.articleservice.controllers;

import com.example.articleservice.entities.Article;
import com.example.articleservice.services.ImpArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/article")
@CrossOrigin
public class ArticleController {
    @Autowired
    ImpArticleService articleService;
    @PostMapping(value = "/addArticle")
    public Article addArticle(@RequestBody Article article){
        return articleService.addArticle(article);
    }
    @PutMapping(value="/updateArticle/{id}")
    public Article updateArticle(@PathVariable Long id, @RequestBody Article article)
    {
        article.setArticleId(id);
        return articleService.updateArticle(article);
    }
    @GetMapping(value = "/findArticle/{id}")
    public Article findOneArticle(@PathVariable Long id)
    {
        return articleService.findArticleById(id);
    }
    @GetMapping(value = "/findArticleByType")
    public Article findArticleByTypeMatchesRegex(@RequestParam String type)
    {
        return articleService.findArticleByTypeMatchesRegex(type);
    }
    @GetMapping(value = "/findArticleByTitle")
    public Article findArticleByTitleMatchesRegex(@RequestParam String title)
    {
        return articleService.findArticleByTitleMatchesRegex(title);
    }

    @GetMapping(value = "/articles")
    public List<Article> findAll()
    {
        return articleService.findAllArticles();
    }
    @DeleteMapping(value = "/deleteArticle/{id}")
    public void deleteArticle(@PathVariable Long id)
    {
        articleService.deleteArticle(id);
    }
    @PutMapping(value="/affectAuthor/{idMember}/{idArticle}")
	public Article affectAuthorToArticle(@PathVariable Long idMember , @PathVariable Long idArticle )
	{
        return articleService.affectAuthorToArticle(idMember, idArticle);
	}
	@GetMapping("/articlesByMember/{idMember}")
	public List<Article> getAllArticlesByMember(@PathVariable Long idMember)
	{
        return articleService.getAllArticlesByMember(idMember);
    }
}
