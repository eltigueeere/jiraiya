package com.mx.jiraiya.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleGrantedAuthorityMixim {

	@JsonCreator
	public SimpleGrantedAuthorityMixim(@JsonProperty("authority") String role) {
		
	}

}
