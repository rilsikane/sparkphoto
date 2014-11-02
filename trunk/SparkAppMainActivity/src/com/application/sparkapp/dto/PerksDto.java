package com.application.sparkapp.dto;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;


public class PerksDto {

@JsonProperty("id")
private String id;
@JsonProperty("id_sponsor")
private String idSponsor;
@JsonProperty("cover_images")
private String coverImages;
@JsonProperty("thumnail_images")
private String thumnailImages;
@JsonProperty("name")
private String name;
@JsonProperty("type")
private String type;
@JsonProperty("short_description")
private String shortDescription;
@JsonProperty("description")
private String description;
@JsonProperty("credit")
private Object credit;
@JsonProperty("code")
private String code;
@JsonProperty("time_expire")
private String timeExpire;
@JsonProperty("time_created")
private String timeCreated;
@JsonProperty("used")
private Boolean used;
@JsonProperty("brandname")
private String brandname;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* 
* @return
* The id
*/
@JsonProperty("id")
public String getId() {
return id;
}

/**
* 
* @param id
* The id
*/
@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

/**
* 
* @return
* The idSponsor
*/
@JsonProperty("id_sponsor")
public String getIdSponsor() {
return idSponsor;
}

/**
* 
* @param idSponsor
* The id_sponsor
*/
@JsonProperty("id_sponsor")
public void setIdSponsor(String idSponsor) {
this.idSponsor = idSponsor;
}

/**
* 
* @return
* The coverImages
*/
@JsonProperty("cover_images")
public String getCoverImages() {
return coverImages;
}

/**
* 
* @param coverImages
* The cover_images
*/
@JsonProperty("cover_images")
public void setCoverImages(String coverImages) {
this.coverImages = coverImages;
}

/**
* 
* @return
* The thumnailImages
*/
@JsonProperty("thumnail_images")
public String getThumnailImages() {
return thumnailImages;
}

/**
* 
* @param thumnailImages
* The thumnail_images
*/
@JsonProperty("thumnail_images")
public void setThumnailImages(String thumnailImages) {
this.thumnailImages = thumnailImages;
}

/**
* 
* @return
* The name
*/
@JsonProperty("name")
public String getName() {
return name;
}

/**
* 
* @param name
* The name
*/
@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

/**
* 
* @return
* The type
*/
@JsonProperty("type")
public String getType() {
return type;
}

/**
* 
* @param type
* The type
*/
@JsonProperty("type")
public void setType(String type) {
this.type = type;
}

/**
* 
* @return
* The shortDescription
*/
@JsonProperty("short_description")
public String getShortDescription() {
return shortDescription;
}

/**
* 
* @param shortDescription
* The short_description
*/
@JsonProperty("short_description")
public void setShortDescription(String shortDescription) {
this.shortDescription = shortDescription;
}

/**
* 
* @return
* The description
*/
@JsonProperty("description")
public String getDescription() {
return description;
}

/**
* 
* @param description
* The description
*/
@JsonProperty("description")
public void setDescription(String description) {
this.description = description;
}

/**
* 
* @return
* The credit
*/
@JsonProperty("credit")
public Object getCredit() {
return credit;
}

/**
* 
* @param credit
* The credit
*/
@JsonProperty("credit")
public void setCredit(Object credit) {
this.credit = credit;
}

/**
* 
* @return
* The code
*/
@JsonProperty("code")
public String getCode() {
return code;
}

/**
* 
* @param code
* The code
*/
@JsonProperty("code")
public void setCode(String code) {
this.code = code;
}

/**
* 
* @return
* The timeExpire
*/
@JsonProperty("time_expire")
public String getTimeExpire() {
return timeExpire;
}

/**
* 
* @param timeExpire
* The time_expire
*/
@JsonProperty("time_expire")
public void setTimeExpire(String timeExpire) {
this.timeExpire = timeExpire;
}

/**
* 
* @return
* The timeCreated
*/
@JsonProperty("time_created")
public String getTimeCreated() {
return timeCreated;
}

/**
* 
* @param timeCreated
* The time_created
*/
@JsonProperty("time_created")
public void setTimeCreated(String timeCreated) {
this.timeCreated = timeCreated;
}

/**
* 
* @return
* The used
*/
@JsonProperty("used")
public Boolean getUsed() {
return used;
}

/**
* 
* @param used
* The used
*/
@JsonProperty("used")
public void setUsed(Boolean used) {
this.used = used;
}



@JsonProperty("brandname")
public String getBrandname() {
	return brandname;
}

public void setBrandname(String brandname) {
	this.brandname = brandname;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
