package com.journal.entity;

import com.journal.dto.ArticleDto;

import java.util.List;

public interface ArticleManager {

    public Article getArticleByArticleId(Integer articleId);

    public Article merge(Article article);

    Article persist(Article article);

    List<Article> getAllArticleByUserId(Integer userId);

    List<Article> getAllArticles();

    List<ArticleDto> getArticleListByIds(List<Integer> articleIdList);

    void updateArticlesStatus(List<Integer> articleIds, Integer status);
}
