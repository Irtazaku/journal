package com.journal.entity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ArticleManagerImpl implements ArticleManager {

    @PersistenceContext()
    private EntityManager entityManager;

    @Autowired
    private ArticleRepository articleRepository;

    //@Override
    public Article getArticleByArticleId(Integer articleId) {
        TypedQuery<Article> query = entityManager
                .createNamedQuery("article.getArticleByArticleId", Article.class)
                .setParameter("articleId", articleId);

        List<Article> articles = query.getResultList();
        return !articles.isEmpty() ? articles.get(0) : null;
    }

    @Override
    public Article merge(Article article) {
        return entityManager.merge(article);
    }

    @Override
    public Article persist(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public List<Article> getAllArticleByUserId(Integer userId) {
        TypedQuery<Article> query = entityManager
                .createNamedQuery("article.getArticleListByUserId", Article.class)
                .setParameter("userId", userId);

        List<Article> articles = query.getResultList();
        return !articles.isEmpty() ? articles : new ArrayList<>();
    }

}
