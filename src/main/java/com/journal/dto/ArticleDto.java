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
    private UserDto user;
    private Integer status;
    private Date createdDate;

    public ArticleDto() {
    }

    public ArticleDto(Integer id, String title, String content, UserDto user,Integer  status, Date createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.status = status;
        this.createdDate = createdDate;
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Article asEntity(){
        return new Article(this.title, this.content, this.user.asEntity(), this.status, this.createdDate);
    }
}