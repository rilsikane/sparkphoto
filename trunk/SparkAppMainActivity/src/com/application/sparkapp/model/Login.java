package com.application.sparkapp.model;

import com.roscopeco.ormdroid.Entity;

public class Login  extends Entity{
	public int id;
	public String ac_token;
	public String loginDt;
	
	public Login(){
		this(null, null);
	}
	
	public Login(String ac_token, String loginDt) {
		this.ac_token = ac_token;
		this.loginDt = loginDt;
	}
	
	

}
