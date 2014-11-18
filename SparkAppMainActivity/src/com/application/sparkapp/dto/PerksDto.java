package com.application.sparkapp.dto;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import android.os.Parcel;
import android.os.Parcelable;

public class PerksDto implements Parcelable {

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
	private String credit;
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
	@JsonProperty("logo_image")
	private String logo_image;
	@JsonProperty("is_spark")
	private String is_spark;
	@JsonProperty("link")
	private String link;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public PerksDto() { 
		
	}
	public PerksDto(Parcel in) { 
        readFromParcel(in); 
	}
	
	
	@JsonProperty("id")
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            The id
	 */
	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return The idSponsor
	 */
	@JsonProperty("id_sponsor")
	public String getIdSponsor() {
		return idSponsor;
	}

	/**
	 * 
	 * @param idSponsor
	 *            The id_sponsor
	 */
	@JsonProperty("id_sponsor")
	public void setIdSponsor(String idSponsor) {
		this.idSponsor = idSponsor;
	}

	/**
	 * 
	 * @return The coverImages
	 */
	@JsonProperty("cover_images")
	public String getCoverImages() {
		return coverImages;
	}

	/**
	 * 
	 * @param coverImages
	 *            The cover_images
	 */
	@JsonProperty("cover_images")
	public void setCoverImages(String coverImages) {
		this.coverImages = coverImages;
	}

	/**
	 * 
	 * @return The thumnailImages
	 */
	@JsonProperty("thumnail_images")
	public String getThumnailImages() {
		return thumnailImages;
	}

	/**
	 * 
	 * @param thumnailImages
	 *            The thumnail_images
	 */
	@JsonProperty("thumnail_images")
	public void setThumnailImages(String thumnailImages) {
		this.thumnailImages = thumnailImages;
	}

	/**
	 * 
	 * @return The name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return The type
	 */
	@JsonProperty("type")
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 *            The type
	 */
	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return The shortDescription
	 */
	@JsonProperty("short_description")
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * 
	 * @param shortDescription
	 *            The short_description
	 */
	@JsonProperty("short_description")
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	/**
	 * 
	 * @return The description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 *            The description
	 */
	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return The credit
	 */
	@JsonProperty("credit")
	public String getCredit() {
		return credit;
	}

	/**
	 * 
	 * @param credit
	 *            The credit
	 */
	@JsonProperty("credit")
	public void setCredit(String credit) {
		this.credit = credit;
	}

	/**
	 * 
	 * @return The code
	 */
	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	/**
	 * 
	 * @param code
	 *            The code
	 */
	@JsonProperty("code")
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 
	 * @return The timeExpire
	 */
	@JsonProperty("time_expire")
	public String getTimeExpire() {
		return timeExpire;
	}

	/**
	 * 
	 * @param timeExpire
	 *            The time_expire
	 */
	@JsonProperty("time_expire")
	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}

	/**
	 * 
	 * @return The timeCreated
	 */
	@JsonProperty("time_created")
	public String getTimeCreated() {
		return timeCreated;
	}

	/**
	 * 
	 * @param timeCreated
	 *            The time_created
	 */
	@JsonProperty("time_created")
	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * 
	 * @return The used
	 */
	@JsonProperty("used")
	public Boolean getUsed() {
		return used;
	}

	/**
	 * 
	 * @param used
	 *            The used
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
	
	
	@JsonProperty("logo_image")
	public String getLogo_image() {
		return logo_image;
	}
	public void setLogo_image(String logo_image) {
		this.logo_image = logo_image;
	}
	@JsonProperty("is_spark")
	public String getIs_spark() {
		return is_spark;
	}
	public void setIs_spark(String is_spark) {
		this.is_spark = is_spark;
	}
	
	
	@JsonProperty("link")
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(id);
		dest.writeString(idSponsor);
		dest.writeString(coverImages);
		dest.writeString(thumnailImages);
		dest.writeString(name);
		dest.writeString(type);
		dest.writeString(shortDescription);
		dest.writeString(description);
		dest.writeString(credit);
		dest.writeString(code);
		dest.writeString(timeExpire);
		dest.writeString(timeCreated);
		dest.writeByte((byte) (used ? 1 : 0));
		dest.writeString(brandname);
		dest.writeString(logo_image);
		dest.writeString(is_spark);
		dest.writeString(link);
	}

	private void readFromParcel(Parcel in) {
		id = in.readString();
		idSponsor = in.readString();
		coverImages = in.readString();
		thumnailImages = in.readString();
		name = in.readString();
		type = in.readString();
		shortDescription = in.readString();
		description = in.readString();
		credit = in.readString();
		code = in.readString();
		timeExpire = in.readString();
		timeCreated = in.readString();
		used = in.readByte() != 0;
		brandname = in.readString();
		logo_image = in.readString();
		is_spark = in.readString();
		link = in.readString();
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public PerksDto createFromParcel(Parcel parcel) {

			return new PerksDto(parcel);
		}

		public PerksDto[] newArray(int size) {
			return new PerksDto[size];
		}
	};
}
