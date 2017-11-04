package com.journal.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Venturedive on 11/09/2017.
 */
public class ArticlesListResponseDto implements Serializable {
    private ResponseHeaderDto responseHeaderDto;
    private List<ArticleDto> articleDtos;

    public ArticlesListResponseDto() {
    }

    public ArticlesListResponseDto(ResponseHeaderDto responseHeaderDto, List<ArticleDto> articleDtos) {
        this.responseHeaderDto = responseHeaderDto;
        this.articleDtos = articleDtos;
    }

    public ResponseHeaderDto getResponseHeaderDto() {
        return responseHeaderDto;
    }

    public void setResponseHeaderDto(ResponseHeaderDto responseHeaderDto) {
        this.responseHeaderDto = responseHeaderDto;
    }

    public List<ArticleDto> getArticleDtos() {
        return articleDtos;
    }

    public void setArticleDtos(List<ArticleDto> articleDtos) {
        this.articleDtos = articleDtos;
    }
}
