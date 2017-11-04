package com.journal.dto;


import com.journal.entity.Article;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Venturedive on 10/29/2017.
 */
public class ArticleDto implements Serializable {
    private Integer id;
    private String title;
    private String content;
    private Date date;

    public ArticleDto() {
    }

    public ArticleDto(Integer id, String title, String content, Date date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Article asEntity(){
        return new Article(this.title, this.content, this.date);
    }
}