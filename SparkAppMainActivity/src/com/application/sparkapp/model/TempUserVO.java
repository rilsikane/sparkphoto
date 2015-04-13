package com.application.sparkapp.model;

import com.roscopeco.ormdroid.Entity;

public class TempUserVO extends Entity{
	public int id;
	public String tutorial;
	
	public TempUserVO(){
		this(null);
	}
	
	public TempUserVO(String tutorial){
		
		this.tutorial = tutorial;
	}
	
	

}
