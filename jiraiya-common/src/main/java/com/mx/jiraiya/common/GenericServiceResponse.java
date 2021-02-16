package com.mx.jiraiya.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.mx.jiraiya.common.serializer.CustomLocalDateTimeDeSerializer;
import com.mx.jiraiya.common.serializer.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;

public class GenericServiceResponse {
	   
	protected String code;
	protected String message;
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeSerializer.class)
	protected LocalDateTime createdRequest;

	public GenericServiceResponse(){
		this.createdRequest = LocalDateTime.now();
	}

	public GenericServiceResponse(String code, String message){
		this.code = code;
		this.message = message;
	}

	public GenericServiceResponse(String code, String message, LocalDateTime createdRequest){
		this.code = code;
		this.message = message;
		this.createdRequest = createdRequest;
	}

	public String getCode() {
	    return code;
	}

	public void setCode(String code) {
	    this.code = code;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
	    this.message = message;
	}

	public LocalDateTime getCreatedRequest() {
		return createdRequest;
	}

	public void setCreatedRequest(LocalDateTime created) {
		this.createdRequest = created;
	}

	@Override
	public String toString() {
		return "{" +
				"\"code\":\"" + code + '\"' +
				", \"message\":\"" + message + '\"' +
				", \"createdRequest\":\"" + createdRequest + '\"' +
				"}";
	}
}
