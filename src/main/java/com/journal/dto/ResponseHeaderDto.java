package com.journal.dto;

import java.io.Serializable;

/**
 * Created by Venturedive on 11/09/2017.
 */
public class ResponseHeaderDto implements Serializable {

    public static final Integer STATUS_OK = 0;
    public static final String STATUS_OK_TEXT = "OK";
    public static final Integer STATUS_NOT_OK = 1;
    public static final String STATUS_NOT_OK_TEXT = "Something Unexpected happened.";
    public static final Integer STATUS_BAD_REQUEST = 2;
    public static final String STATUS_BAD_REQUEST_TEXT = "Incorrect Request Payload.";
    public static final Integer STATUS_INVALID_LOGIN_DETAILS = 3;
    public static final String STATUS_INVALID_LOGIN_DETAILS_TEXT = "Invalid username or password.";
    public static final Integer STATUS_USER_ALREADY_EXIXST = 4;
    public static final String STATUS_USER_ALREADY_EXIXST_TEXT = "User already exists.";
    public static final Integer STATUS_USER_NOT_AUTHORIZED = 5;
    public static final String STATUS_USER_NOT_AUTHORIZED_TEXT = "User not authorized";

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
