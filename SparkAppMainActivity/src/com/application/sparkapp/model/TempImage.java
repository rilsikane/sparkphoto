package com.application.sparkapp.model;

import com.roscopeco.ormdroid.Entity;

public class TempImage extends Entity{
	public int id;
	public String ac_token;
	public String path;
	public String amt;
	public String loginDt;
	public String originPath;
	
	public TempImage(){
		this(null, null, null, null, null);
	}
	
	public TempImage(String ac_token, String path, String amt, String loginDt,String originPath) {
		this.ac_token = ac_token;
		this.path = path;
		this.amt = amt;
		this.loginDt = loginDt;
		this.originPath = originPath;
	}
	
	

}
