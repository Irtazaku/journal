package com.journal.entity;


import com.journal.dto.ArticleDto;
import com.journal.util.ConstantsAndEnums.GlobalConstants;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Venturedive on 10/29/2017.
 */

@Entity
@Table(name = "articles")
@NamedQueries({
        @NamedQuery(name = "article.getArticleByArticleId",
                query = " SELECT a" +
                        " FROM Article a " +
                        " WHERE a.id = :articleId " +
                        " and a.status in :notDeletedIds"),
        @NamedQuery(name = "article.getArticleListByUserId",
                query = " SELECT a" +
                        " FROM Article a " +
                        " WHERE a.user.id = :userId " +
                        " And a.status in :notDeletedIds" +
                        " order by a.status asc, a.createdDate desc"),
        @NamedQuery(name = "article.getAllArticles",
                query = " SELECT a" +
                        " FROM Article a " +
                        " WHERE a.status in :notDeletedIds" +
                        " order by a.status asc, a.createdDate desc"),
        @NamedQuery(name = "article.getArticleListByIds",
                query = " SELECT a" +
                        " FROM Article a " +
                        " WHERE a.status in :notRejectedIds" +
                        " and a.id in :articleIdList")
})
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;
    @Lob
    private String content;

    @ManyToOne(optional = true)
    @JoinColumn(name = "author_user_id", referencedColumnName = "id")
    private User user;

    private Integer status = GlobalConstants.ARTICLE_STATUS_PENDDING;

    private Date createdDate;

    public Article() {
    }

    public Article(String title, String content, User user, Integer status, Date createdDate) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.status = status;
        this.createdDate = (createdDate == null) ? new Date() : createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ArticleDto asDto(){
        return new ArticleDto(this.id, this.title, this.content, this.user.asDto(), this.status, this.createdDate);
    }
}