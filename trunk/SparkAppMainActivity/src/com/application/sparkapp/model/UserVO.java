package com.application.sparkapp.model;

import org.codehaus.jackson.annotate.JsonProperty;

import com.application.sparkapp.dto.UserDto;
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
	public String creditExtra;
	public String numberPictureCanUpload;
	public String nextTimeCanUpload;
	public String normalCredit;
	public String tutorial;
	public String status;
	
	public UserVO(){
		this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,null,null,null,null,null,null);
	}
	
	public UserVO(String method, String email, String password,
			String firstname, String lastname, String nric_fin, String gender,
			String birthday, String phone, String phone_service,
			String occupation, String address_block,
			String address_street_name, String address_unit_number,
			String address_postal, String fb_access_token,String creditExtra, String ac_token,String numberPictureCanUpload, 
			String nextTimeCanUpload,String normalCredit,String tutorial,String status){
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
		this.creditExtra = creditExtra;
		this.numberPictureCanUpload= numberPictureCanUpload;
		this.nextTimeCanUpload = nextTimeCanUpload;
		this.normalCredit = normalCredit;
		this.tutorial = tutorial;
		this.status = status;
	}
	
	public UserVO convertDtoToVo(UserDto dto){
		UserVO vo = this;
		vo.email = dto.getEmail();
		vo.password = dto.getPassword();
		vo.firstname = dto.getFirstname();
		vo.lastname = dto.getLastname();
		vo.nric_fin = dto.getNric_fin();
		vo.gender = dto.getGender();
		vo.birthday = dto.getBirthday();
		vo.phone = dto.getPhone();
		vo.phone_service = dto.getPhone_service();
		vo.occupation = dto.getOccupation();
		vo.address_block = dto.getAddress_block();
		vo.address_street_name = dto.getAddress_street_name();
		vo.address_unit_number = dto.getAddress_unit_number();
		vo.address_postal = dto.getAddress_postal();
		vo.fb_access_token = dto.getFb_access_token();
		vo.creditExtra = dto.getCreditExtra();
		vo.numberPictureCanUpload= dto.getNumberPictureCanUpload()+"";
		vo.nextTimeCanUpload = dto.getNextTimeCanUpload();
		vo.normalCredit = dto.getNormalCredit()? "T":"F";
		vo.ac_token = dto.getAccess_token();
		
		return vo;
	}

}
