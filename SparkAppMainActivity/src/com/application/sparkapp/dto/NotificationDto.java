package com.application.sparkapp.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class NotificationDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6581936925077053390L;
	@JsonProperty("id")
	private String id;
	@JsonProperty("id_user")
	private String id_user;
	@JsonProperty("type")
	private String type;
	@JsonProperty("id_node")
	private String id_node;
	@JsonProperty("time_created")
	private String time_created;
	@JsonProperty("message")
	private String message;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId_user() {
		return id_user;
	}
	public void setId_user(String id_user) {
		this.id_user = id_user;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId_node() {
		return id_node;
	}
	public void setId_node(String id_node) {
		this.id_node = id_node;
	}
	public String getTime_created() {
		return time_created;
	}
	public void setTime_created(String time_created) {
		this.time_created = time_created;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
		
}
