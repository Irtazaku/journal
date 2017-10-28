package com.journal.dto;

/**
 * Created by Venturedive on 11/09/2017.
 */
public class ResponseHeaderDto {

    public static final Integer STATUS_OK = 0;
    public static final Integer STATUS_NOT_OK = 1;
    public static final String STATUS_OK_TEXT = "OK";
    public static final String STATUS_NOT_OK_TEXT = "Something Unexpected happened.";

    private Integer statusCode;
    private String message;
    private Boolean isError;

    public ResponseHeaderDto() {
    }

    public ResponseHeaderDto(Integer statusCode, String message, Boolean isError) {
        this.statusCode = statusCode;
        this.message = message;
        this.isError = isError;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsError() {
        return isError;
    }

    public void setIsError(Boolean isError) {
        this.isError = isError;
    }
}
