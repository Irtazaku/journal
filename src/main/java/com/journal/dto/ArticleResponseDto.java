package com.journal.dto;

import java.io.Serializable;

/**
 * Created by Venturedive on 11/09/2017.
 */
public class ArticleResponseDto implements Serializable {
    private ResponseHeaderDto responseHeaderDto;
    private ArticleDto articleDto;

    public ArticleResponseDto() {
    }

    public ArticleResponseDto(ResponseHeaderDto responseHeaderDto, ArticleDto articleDto) {
        this.responseHeaderDto = responseHeaderDto;
        this.articleDto = articleDto;
    }

    public ResponseHeaderDto getResponseHeaderDto() {
        return responseHeaderDto;
    }

    public void setResponseHeaderDto(ResponseHeaderDto responseHeaderDto) {
        this.responseHeaderDto = responseHeaderDto;
    }

    public ArticleDto getArticleDto() {
        return articleDto;
    }

    public void setArticleDto(ArticleDto articleDto) {
        this.articleDto = articleDto;
    }
}
