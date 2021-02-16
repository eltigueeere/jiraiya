package com.mx.jiraiya.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
//@Table(name = "authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "authority"})})
@Table(name = "authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"authority"})})
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String authority;
	
	/*private Long user_id;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}