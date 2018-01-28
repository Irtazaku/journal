package com.journal.entity;


import com.journal.dto.ArticleDto;
import com.journal.util.ConstantsAndEnums.GlobalConstants;
import com.journal.util.Util;
import org.apache.log4j.Logger;
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

    public static final Logger LOGGER= Logger.getLogger(ArticleManagerImpl.class);

    @PersistenceContext()
    private EntityManager entityManager;

    @Autowired
    private ArticleRepository articleRepository;

    //@Override
    public Article getArticleByArticleId(Integer articleId) {
        TypedQuery<Article> query = entityManager
                .createNamedQuery("article.getArticleByArticleId", Article.class)
                .setParameter("notDeletedIds", Util.getNotDeletedArticleStatusIds())
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
                .setParameter("notDeletedIds", Util.getNotDeletedArticleStatusIds())
                .setParameter("userId", userId);

        List<Article> articles = query.getResultList();
        return !articles.isEmpty() ? articles : new ArrayList<>();
    }

    @Override
    public List<Article> getAllArticles() {
        TypedQuery<Article> query = entityManager
            .createNamedQuery("article.getAllArticles", Article.class)
            .setParameter("notDeletedIds", Util.getNotDeletedArticleStatusIds());

        List<Article> articles = query.getResultList();
        return !articles.isEmpty() ? articles : new ArrayList<>();
    }

    @Override
    public List<ArticleDto> getArticleListByIds(List<Integer> articleIdList) {
        TypedQuery<Article> query = entityManager
                .createNamedQuery("article.getArticleListByIds", Article.class)
                .setParameter("notRejectedIds", Util.getNotRejectedArticleStatusIds())
                .setParameter("articleIdList", articleIdList);

        List<Article> articles = query.getResultList();
        List<ArticleDto> articleDtos = new ArrayList<>();
        for(Article article: articles){
            articleDtos.add(article.asDto());
        }
        return articleDtos;
    }

    @Override
    public void updateArticlesStatus(List<Integer> articleIds, Integer status) {
        entityManager.createNamedQuery("article.updateArticleStatus")
                .setParameter("status", status)
                .setParameter("idList", articleIds)
                .executeUpdate();
    }

}
