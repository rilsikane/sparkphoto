package com.application.sparkapp.dto;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import android.os.Parcel;
import android.os.Parcelable;


public class UserDto implements Parcelable{
	@JsonIgnore
	private String method;
	@JsonProperty("email")
	private String email;
	private String password;
	@JsonProperty("firstname")
	private String firstname;
	@JsonProperty("lastname")
	private String lastname;
	@JsonProperty("nric_fin")
	private String nric_fin;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("birthday")
	private String birthday;
	@JsonProperty("phone")
	private String phone;
	@JsonProperty("phone_service")
	private String phone_service;
	@JsonProperty("occupation")
	private String occupation;
	@JsonProperty("address_block")
	private String address_block;
	@JsonProperty("address_street_name")
	private String address_street_name;
	@JsonProperty("address_unit_number")
	private String address_unit_number;
	@JsonProperty("address_postal")
	private String address_postal;
	@JsonIgnore
	private String fb_access_token;
	@JsonProperty("credit_extra")
	private String creditExtra;
	@JsonProperty("number_picture_can_upload")
	private int numberPictureCanUpload;
	@JsonProperty("next_time_can_upload")
	private String nextTimeCanUpload;
	@JsonProperty("normal_credit")
	private boolean normalCredit;
	@JsonIgnore
	private String access_token;
	public UserDto() { 
		
	}
	public UserDto(Parcel in) { 
        readFromParcel(in); 
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getNric_fin() {
		return nric_fin;
	}
	public void setNric_fin(String nric_fin) {
		this.nric_fin = nric_fin;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone_service() {
		return phone_service;
	}
	public void setPhone_service(String phone_service) {
		this.phone_service = phone_service;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getAddress_block() {
		return address_block;
	}
	public void setAddress_block(String address_block) {
		this.address_block = address_block;
	}
	public String getAddress_street_name() {
		return address_street_name;
	}
	public void setAddress_street_name(String address_street_name) {
		this.address_street_name = address_street_name;
	}
	public String getAddress_unit_number() {
		return address_unit_number;
	}
	public void setAddress_unit_number(String address_unit_number) {
		this.address_unit_number = address_unit_number;
	}
	public String getAddress_postal() {
		return address_postal;
	}
	public void setAddress_postal(String address_postal) {
		this.address_postal = address_postal;
	}
	public String getFb_access_token() {
		return fb_access_token;
	}
	public void setFb_access_token(String fb_access_token) {
		this.fb_access_token = fb_access_token;
	}
	
	public String getCreditExtra() {
		return creditExtra;
	}
	public void setCreditExtra(String creditExtra) {
		this.creditExtra = creditExtra;
	}
	public int getNumberPictureCanUpload() {
		return numberPictureCanUpload;
	}
	public void setNumberPictureCanUpload(int numberPictureCanUpload) {
		this.numberPictureCanUpload = numberPictureCanUpload;
	}
	public String getNextTimeCanUpload() {
		return nextTimeCanUpload;
	}
	public void setNextTimeCanUpload(String nextTimeCanUpload) {
		this.nextTimeCanUpload = nextTimeCanUpload;
	}
	public boolean getNormalCredit() {
		return normalCredit;
	}
	public void setNormalCredit(boolean normalCredit) {
		this.normalCredit = normalCredit;
	}
	
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(method);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(nric_fin);
        dest.writeString(gender);
        dest.writeString(birthday);
        dest.writeString(phone);
        dest.writeString(phone_service);
        dest.writeString(occupation);
        dest.writeString(address_block);
        dest.writeString(address_street_name);
        dest.writeString(address_unit_number);
        dest.writeString(address_postal);
        dest.writeString(fb_access_token);
        dest.writeString(creditExtra);
        dest.writeInt(numberPictureCanUpload);
        dest.writeString(nextTimeCanUpload);
        dest.writeByte((byte) (normalCredit ? 1 : 0)); 
        dest.writeString(access_token);
	}
	private void readFromParcel(Parcel in) {
		method = in.readString();
		email = in.readString();
		password = in.readString();
		firstname = in.readString();
		lastname = in.readString();
		nric_fin = in.readString();
		gender = in.readString();
		birthday = in.readString();
		phone = in.readString();
		phone_service = in.readString();
		occupation = in.readString();
		address_block = in.readString();
		address_street_name = in.readString();
		address_unit_number = in.readString();
		address_postal = in.readString();
		fb_access_token = in.readString();
		creditExtra = in.readString();
		numberPictureCanUpload = in.readInt();
		nextTimeCanUpload = in.readString();
		normalCredit = in.readByte() != 0; 
		access_token = in.readString();
	}
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserDto createFromParcel(Parcel parcel) {
           
            return new UserDto(parcel);
        }

        public UserDto[] newArray(int size) {
            return new UserDto[size];
        }
    };
	
	
}
