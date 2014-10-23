package com.application.sparkapp.model;

import com.roscopeco.ormdroid.Entity;

public class UserVO extends Entity{
	public int id;
	public String method;
	public String email;
	public String password;
	public String firstname;
	public String lastname;
	public String nric_fin;
	public String gender;
	public String birthday;
	public String phone;
	public String phone_service;
	public String occupation;
	public String address_block;
	public String address_street_name;
	public String address_unit_number;
	public String address_postal;
	public String fb_access_token;
	public String ac_token;
	
	public UserVO(){
		this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
	}
	
	public UserVO(String method, String email, String password,
			String firstname, String lastname, String nric_fin, String gender,
			String birthday, String phone, String phone_service,
			String occupation, String address_block,
			String address_street_name, String address_unit_number,
			String address_postal, String fb_access_token, String ac_token) {
		this.method = method;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.nric_fin = nric_fin;
		this.gender = gender;
		this.birthday = birthday;
		this.phone = phone;
		this.phone_service = phone_service;
		this.occupation = occupation;
		this.address_block = address_block;
		this.address_street_name = address_street_name;
		this.address_unit_number = address_unit_number;
		this.address_postal = address_postal;
		this.fb_access_token = fb_access_token;
		this.ac_token = ac_token;
	}
	
	

}
