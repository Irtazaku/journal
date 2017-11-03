package com.journal.util;

import com.journal.dto.ResponseHeaderDto;

public enum ResponseStatusCodeEnum {

	SUCCESS(ResponseHeaderDto.STATUS_OK, ResponseHeaderDto.STATUS_OK_TEXT, Boolean.TRUE),
	ERROR(ResponseHeaderDto.STATUS_NOT_OK, ResponseHeaderDto.STATUS_NOT_OK_TEXT, Boolean.FALSE),
	BAD_REQUEST(ResponseHeaderDto.STATUS_BAD_REQUEST, ResponseHeaderDto.STATUS_BAD_REQUEST_TEXT, Boolean.FALSE),
	INVALID_LOGIN_DETAILS(ResponseHeaderDto.STATUS_INVALID_LOGIN_DETAILS, ResponseHeaderDto.STATUS_INVALID_LOGIN_DETAILS_TEXT, Boolean.FALSE);

	private Integer statusCode;
	private String message;
	private Boolean isError;
	
	private ResponseStatusCodeEnum(Integer statusCode, String message, Boolean isError) {
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
	
	public ResponseHeaderDto getHeader(){
		return new ResponseHeaderDto(this.statusCode, this.message, this.isError);
	}
	
	@Override
	public String toString(){
		return "statusCode: '" + this.statusCode + "', message: '" + this.message + "', isError: '" + this.isError + "'";
	}
}
