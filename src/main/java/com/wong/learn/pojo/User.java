package com.wong.learn.pojo;

import java.io.Serializable;

public class User implements Serializable{
	
	private static final long serialVersionUID = -1858118754739868685L;
	private String username;
	private String password;
	
	public User(String username,String password){
		this.username = username;
		this.password = password;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
