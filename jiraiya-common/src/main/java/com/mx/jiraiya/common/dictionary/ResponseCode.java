package com.mx.jiraiya.common.dictionary;


import com.mx.jiraiya.common.GenericServiceResponse;

public enum ResponseCode {

	OK("00","Success"),
	BAD_REQUEST("BAD_REQUEST","Invalid Request"),

	/* RESPONSE SERVER ERROR */
	OTHER_ERROR("OTHER_ERROR","Error Services!"),
	ERROR_400("ERROR_400","Bad Request"),
	ERROR_401("ERROR_401","Unauthorized"),
	ERROR_403("ERROR_403","Forbidden!"),
	ERROR_404("ERROR_404","Not Found"),
	ERROR_405("ERROR_405","Method Not Allowed"),
	ERROR_500("ERROR_500","Internal Error");

	

    private ResponseCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public final String code;
	public final String desc;

	public void buildServiceResponse(GenericServiceResponse serviceResponse){
		serviceResponse.setCode(this.code);
		serviceResponse.setMessage(this.desc);
	}
	
	
}
