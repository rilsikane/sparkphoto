package com.application.sparkapp.json;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.PerksDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.model.Login;
import com.application.sparkapp.model.UserVO;
import com.application.sparkapp.util.DateUtil;
import com.application.sparkapp.util.GlobalVariable;

import android.app.Activity;
import android.os.Environment;
import android.text.Html;
import android.util.Log;

public class JSONParserForGetList {
	public Activity a;
	public String news_cat;

	private static JSONParserForGetList instance = null;
	private static HttpClient httpclient = null;
	// constructor
	protected JSONParserForGetList() {
	      // Exists only to defeat instantiation.
	}
	public static JSONParserForGetList getInstance() {
	      if(instance == null) {
	         instance = new JSONParserForGetList();
	      }
	      return instance;
	}
	public static HttpClient getClientInstance(){
		  if(httpclient == null) {
			  httpclient = new DefaultHttpClient();
		      }
		      return httpclient;
	}
	
	public CommonDto Register(UserDto userDto){
		CommonDto commonDto = new CommonDto();
		try{
		 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
         nameValuePairs.add(new BasicNameValuePair("method", "register"));
         nameValuePairs.add(new BasicNameValuePair("email", userDto.getEmail()));
         nameValuePairs.add(new BasicNameValuePair("password", userDto.getPassword()));
         nameValuePairs.add(new BasicNameValuePair("firstname", userDto.getFirstname()));
         nameValuePairs.add(new BasicNameValuePair("lastname", userDto.getLastname()));
         nameValuePairs.add(new BasicNameValuePair("nric_fin", userDto.getNric_fin()));
         nameValuePairs.add(new BasicNameValuePair("gender", userDto.getGender()));
         nameValuePairs.add(new BasicNameValuePair("birthday", userDto.getBirthday()));
         nameValuePairs.add(new BasicNameValuePair("phone", userDto.getPhone()));
         nameValuePairs.add(new BasicNameValuePair("phone_service", userDto.getPhone_service()));
         nameValuePairs.add(new BasicNameValuePair("occupation", userDto.getOccupation()));
         nameValuePairs.add(new BasicNameValuePair("address_block", userDto.getAddress_block()));
         nameValuePairs.add(new BasicNameValuePair("address_street_name", userDto.getAddress_street_name()));
         nameValuePairs.add(new BasicNameValuePair("address_unit_number", userDto.getAddress_unit_number()));
         nameValuePairs.add(new BasicNameValuePair("address_postal", userDto.getAddress_postal()));
         nameValuePairs.add(new BasicNameValuePair("confirm_term_of_use", userDto.isConfrim()+""));
         
         if(userDto.getFb_access_token()!=null && !"".equals(userDto.getFb_access_token())){
        	 nameValuePairs.add(new BasicNameValuePair("fb_access_token", userDto.getFb_access_token()));
         }
         
         JSONObject json = getJsonFromUrlDoPost(GlobalVariable.URL_REGISTER, nameValuePairs);
         
         if(!json.isNull("success")){
        	 commonDto.setFlag(true);
        	 commonDto.setToken(json.getString("app_access_token"));
         }else{
        	 commonDto.setFlag(false);
        	 commonDto.setMsg(json.getString("message"));
         }
		}catch (Exception e) {
			e.printStackTrace();
		}
         return commonDto;
	}
	public CommonDto EditProfile(UserDto userDto,String debug){
		CommonDto commonDto = new CommonDto();
		try{
		 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
         nameValuePairs.add(new BasicNameValuePair("method", "editProfile"));
         nameValuePairs.add(new BasicNameValuePair("ac", userDto.getAccess_token()));
         nameValuePairs.add(new BasicNameValuePair("firstname", userDto.getFirstname()));
         nameValuePairs.add(new BasicNameValuePair("lastname", userDto.getLastname()));
         nameValuePairs.add(new BasicNameValuePair("nric_fin", userDto.getNric_fin()));
         nameValuePairs.add(new BasicNameValuePair("gender", userDto.getGender()));
         nameValuePairs.add(new BasicNameValuePair("birthday", userDto.getBirthday()));
         nameValuePairs.add(new BasicNameValuePair("phone", userDto.getPhone()));
         nameValuePairs.add(new BasicNameValuePair("phone_service", userDto.getPhone_service()));
         nameValuePairs.add(new BasicNameValuePair("occupation", userDto.getOccupation()));
         nameValuePairs.add(new BasicNameValuePair("address_block", userDto.getAddress_block()));
         nameValuePairs.add(new BasicNameValuePair("address_street_name", userDto.getAddress_street_name()));
         nameValuePairs.add(new BasicNameValuePair("address_unit_number", userDto.getAddress_unit_number()));
         nameValuePairs.add(new BasicNameValuePair("address_postal", userDto.getAddress_postal()));
         nameValuePairs.add(new BasicNameValuePair("debug", debug));
         
         if(userDto.getFb_access_token()!=null && !"".equals(userDto.getFb_access_token())){
        	 nameValuePairs.add(new BasicNameValuePair("fb_access_token", userDto.getFb_access_token()));
         }
         
         JSONObject json = getJsonFromUrlDoPost(GlobalVariable.URL_USERSTATUS, nameValuePairs);
         
         if(!json.isNull("success")){
        	 commonDto.setFlag(true);
        	 commonDto.setToken(json.getString("app_access_token"));
         }else{
        	 commonDto.setFlag(false);
        	 if(!json.isNull("message")){
        		 commonDto.setMsg(json.getString("message"));
        	 }else{
        		 commonDto.setMsg("Error!");
        	 }
         }
		}catch (Exception e) {
			e.printStackTrace();
		}
         return commonDto;
	}
	public CommonDto SubmitOrder(UserVO userDto,List<String>images){
		CommonDto commonDto = new CommonDto();
		try{
		 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
         nameValuePairs.add(new BasicNameValuePair("method", "submitOrder"));
         nameValuePairs.add(new BasicNameValuePair("email", userDto.email));
         nameValuePairs.add(new BasicNameValuePair("firstname", userDto.firstname));
         nameValuePairs.add(new BasicNameValuePair("lastname", userDto.lastname));
         nameValuePairs.add(new BasicNameValuePair("nric_fin", userDto.nric_fin));
         nameValuePairs.add(new BasicNameValuePair("gender", userDto.gender));
         nameValuePairs.add(new BasicNameValuePair("birthday", userDto.birthday));
         nameValuePairs.add(new BasicNameValuePair("phone", userDto.phone));
         nameValuePairs.add(new BasicNameValuePair("phone_service", userDto.phone_service));
         nameValuePairs.add(new BasicNameValuePair("address_block", userDto.address_block));
         nameValuePairs.add(new BasicNameValuePair("address_street_name", userDto.address_street_name));
         nameValuePairs.add(new BasicNameValuePair("address_unit_number", userDto.address_unit_number));
         nameValuePairs.add(new BasicNameValuePair("address_postal", userDto.address_postal));
         nameValuePairs.add(new BasicNameValuePair("ac", userDto.ac_token));
         for(int i=0;i<images.size();i++){
        	 nameValuePairs.add(new BasicNameValuePair("images[]", images.get(i)));
         }
         
         
         
         JSONObject json = getJsonFromUrlDoPost(GlobalVariable.URL_USERSTATUS, nameValuePairs);
         
         if(!json.isNull("success")){
        	 commonDto.setFlag(true);
        	 if(!json.isNull("success")){
        	 commonDto.setToken(json.getString("app_access_token"));
        	 }
         }else{
        	 commonDto.setFlag(false);
        	 commonDto.setMsg(json.getString("message"));
         }
		}catch (Exception e) {
			e.printStackTrace();
		}
         return commonDto;
	}
	public UserDto Login(UserDto userDto){
		CommonDto commonDto = new CommonDto();
		UserDto user = null;
		try{
		 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
         nameValuePairs.add(new BasicNameValuePair("method", "login"));
         if(userDto.getFb_access_token()!=null && !"".equals(userDto.getFb_access_token())){
        	 nameValuePairs.add(new BasicNameValuePair("fb_access_token", userDto.getFb_access_token()));
         }else{
	         nameValuePairs.add(new BasicNameValuePair("email", userDto.getEmail()));
	         nameValuePairs.add(new BasicNameValuePair("password", userDto.getPassword()));
	      }
         
         JSONObject json = getJsonFromUrlDoPost(GlobalVariable.URL_REGISTER, nameValuePairs);
         
         if(!json.isNull("success")){
        	 commonDto.setFlag(true);
        	 commonDto.setToken(json.getString("app_access_token"));
        	 Login login = new Login();
			  login.ac_token = commonDto.getToken();
			  login.loginDt = DateUtil.toStringEngDateSimpleFormat(new Date());
			  login.save();
			  nameValuePairs = new ArrayList<NameValuePair>(2);
		      nameValuePairs.add(new BasicNameValuePair("method", "userStatus"));
		      nameValuePairs.add(new BasicNameValuePair("ac", commonDto.getToken()));
		      JSONObject jsUser = getJsonFromUrlDoPost(GlobalVariable.URL_USERSTATUS, nameValuePairs);
		      
		      if(!jsUser.isNull("user")){
		    	 
		    	  user = (UserDto) getDataMappingToObject(jsUser, UserDto.class, "user");
		    	  user.setAccess_token(commonDto.getToken());
		    	  if(userDto.getFb_access_token()!=null && !"".equals(userDto.getFb_access_token())){
		    		  user.setFb_access_token(userDto.getFb_access_token());
		    	  }
		      }
		      
			  
         }else{
        	 commonDto.setFlag(false);
         }
		}catch (Exception e) {
			e.printStackTrace();
		}
         return user;
	}
	
	public List<PerksDto> getListPerks(String acToken,String type){
		List<PerksDto> perksList = new ArrayList<PerksDto>();;
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("method", "listPerksM"));
	    nameValuePairs.add(new BasicNameValuePair("ac", acToken));
	    nameValuePairs.add(new BasicNameValuePair("type", type));
	    JSONArray jsonArr = getJsonArrayFromUrlDoPost(GlobalVariable.URL_USERSTATUS, nameValuePairs,"perks");
	    try{
		    if(jsonArr!=null){
		    	for(int i=0;i<jsonArr.length();i++){
		    		PerksDto perks = new PerksDto();
		    		JSONObject json =jsonArr.getJSONObject(i);
		    		perks = (PerksDto) getDataMappingToObject(json, PerksDto.class);
		    		perksList.add(perks);
		    	}
		    }
	    }catch (Exception e) {
			e.printStackTrace();
		}
		return perksList;
	}
	public UserDto ReedeemCode(String code,String id_perk,String acCode){
		
		UserDto user = null;
		try{
		 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
         nameValuePairs.add(new BasicNameValuePair("method", "requestPerks"));
         nameValuePairs.add(new BasicNameValuePair("ac", acCode));
         nameValuePairs.add(new BasicNameValuePair("id_perk", id_perk));
         nameValuePairs.add(new BasicNameValuePair("code", code));
         
         
         JSONObject json = getJsonFromUrlDoPost(GlobalVariable.URL_USERSTATUS, nameValuePairs);
         
         if(!json.isNull("success")){
        	
			  nameValuePairs = new ArrayList<NameValuePair>(2);
		      nameValuePairs.add(new BasicNameValuePair("method", "userStatus"));
		      nameValuePairs.add(new BasicNameValuePair("ac", acCode));
		      JSONObject jsUser = getJsonFromUrlDoPost(GlobalVariable.URL_USERSTATUS, nameValuePairs);
		      
		      if(!jsUser.isNull("user")){
		    	 
		    	  user = (UserDto) getDataMappingToObject(jsUser, UserDto.class, "user");
		    	  user.setAccess_token(acCode);
		      }
		      
			  
         }
		}catch (Exception e) {
			e.printStackTrace();
		}
         return user;
	}
	public UserDto getUserStatus(String acCode){
		UserDto user = null;
		try{
			 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				  nameValuePairs = new ArrayList<NameValuePair>(2);
			      nameValuePairs.add(new BasicNameValuePair("method", "userStatus"));
			      nameValuePairs.add(new BasicNameValuePair("ac", acCode));
			      JSONObject jsUser = getJsonFromUrlDoPost(GlobalVariable.URL_USERSTATUS, nameValuePairs);
			      
			      if(!jsUser.isNull("user")){
			    	 
			    	  user = (UserDto) getDataMappingToObject(jsUser, UserDto.class, "user");
			    	  user.setAccess_token(acCode);
			      }
			      
				  
	         
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		return user;
		
	}
	

	public static <T> Object getDataMappingToObject(JSONObject jsonData, Class<T> objClass) {

		Object objectData = null;
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			objectData = mapper.readValue(jsonData.toString(), objClass);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return objectData;
	}
	public static <T> Object getDataMappingToObject(JSONObject jsonData, Class<T> objClass, String variableName) {

		Object objectData = null;
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			objectData = mapper.readValue(jsonData.getJSONObject(variableName).toString(), objClass);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return objectData;
	}
	
	public JSONObject postResultByHttp(String url,String data) {
		InputStream is = null;
		String result = "";
		JSONObject jObject = null;
		try {
			System.out.println("----"+data);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("result", data));
			HttpClient httpclient = getClientInstance();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			int count =0;
			while (((line = reader.readLine()) != null) && count <20) {
				sb.append(line + "\n");
				count++;
			}
			is.close();
			result = sb.toString();
			jObject = new JSONObject(result);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return jObject;
	}
	
	public JSONObject getJsonFromUrlDoGet(String url) {

		InputStream is = null;
		String result = "";
		JSONArray jArray = null;
		JSONObject jObject = null;
		try {
			HttpClient httpclient = getClientInstance();
			HttpGet httppost = new HttpGet(url);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				// System.out.println("Show Result::::"+line);
			}
			is.close();
			result = sb.toString();
			jObject = new JSONObject(result);
			
	
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		 
		return jObject;
	}
	 public JSONObject getJsonFromUrlDoPost(String url,List<NameValuePair> nameValuePairs) {

         InputStream is = null;
         String result = "";
         JSONArray jArray = null;
         JSONObject jObject = null;
         try {
                 HttpClient httpclient = getClientInstance();
                 HttpPost httppost = new HttpPost(url);
                 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                 HttpResponse response = httpclient.execute(httppost);
                 HttpEntity entity = response.getEntity();
                 is = entity.getContent();
         } catch (Exception e) {
                 Log.e("log_tag", "Error in http connection " + e.toString());
         }

         try {
                 BufferedReader reader = new BufferedReader(new InputStreamReader(
                                 is, "iso-8859-1"), 8);
                 StringBuilder sb = new StringBuilder();
                 String line = null;
                 while ((line = reader.readLine()) != null) {
                         sb.append(line + "\n");
                         // System.out.println("Show Result::::"+line);
                 }
                 is.close();
                 result = sb.toString();
                 jObject = new JSONObject(result);
                 
 
         } catch (Exception e) {
                 Log.e("log_tag", "Error converting result " + e.toString());
         }

       
         return jObject;
 }
	 public JSONArray getJsonArrayFromUrlDoPost(String url,List<NameValuePair> nameValuePairs,String jsonArray) {

         InputStream is = null;
         String result = "";
         JSONArray jArray = null;
         JSONObject jObject = null;
         try {
                 HttpClient httpclient = getClientInstance();
                 HttpPost httppost = new HttpPost(url);
                 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                 HttpResponse response = httpclient.execute(httppost);
                 HttpEntity entity = response.getEntity();
                 is = entity.getContent();
         } catch (Exception e) {
                 Log.e("log_tag", "Error in http connection " + e.toString());
         }

         try {
                 BufferedReader reader = new BufferedReader(new InputStreamReader(
                                 is, "iso-8859-1"), 8);
                 StringBuilder sb = new StringBuilder();
                 String line = null;
                 while ((line = reader.readLine()) != null) {
                         sb.append(line + "\n");
                         // System.out.println("Show Result::::"+line);
                 }
                 is.close();
                 result = sb.toString();
                 jObject = new JSONObject(result);
                 
 
         } catch (Exception e) {
                 Log.e("log_tag", "Error converting result " + e.toString());
         }

         try {
                 jArray = jObject.getJSONArray(jsonArray);
         } catch (Exception e) {
                 Log.e("log_tag", "Error parsing data " + e.toString());
         }
         return jArray;
 }
	
	
	public JSONArray getJsonArrayFromUrlDoGet(String url,String jsonArray) {

		InputStream is = null;
		String result = "";
		JSONArray jArray = null;
		JSONObject jObject = null;
		try {
			HttpClient httpclient = getClientInstance();
			HttpGet httppost = new HttpGet(url);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				// System.out.println("Show Result::::"+line);
			}
			is.close();
			result = sb.toString();
			jObject = new JSONObject(result);
			
	
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		try {
			jArray = jObject.getJSONArray(jsonArray);
		} catch (Exception e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return jArray;
	}


	public String getImageSrc(String s){
		String word0= "";
		if(s != null && !"".equals(s)){
		Pattern p=null;
		Matcher m= null;
		
		String word1= null;
		p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
		m = p.matcher(s);
		if (m.find()) {
			word0 = m.group(1);
		}
		}
		return word0;
	}
	
	public long getDiffDay(Date d){
		 long diffDays = 0L;
		if(d!=null){
			Calendar calendar1 = Calendar.getInstance();
		    Calendar calendar2 = Calendar.getInstance();
		    calendar1.set(calendar1.get(Calendar.YEAR), d.getMonth(), d.getDate());
		    long milliseconds1 = calendar1.getTimeInMillis();
		    long milliseconds2 = calendar2.getTimeInMillis();
		    long diff = milliseconds2 - milliseconds1;
		    diffDays = diff / (24 * 60 * 60 * 1000);
		}
		return diffDays;
	}
	
	public String stripHtml(String html) {
	    return Html.fromHtml(html).toString();
	}
	public String loadJSONFromAsset() {
	    String json = null;
	    try {

	        InputStream is = a.getAssets().open("result.json");

	        int size = is.available();

	        byte[] buffer = new byte[size];

	        is.read(buffer);

	        is.close();

	        json = new String(buffer, "UTF-8");


	    } catch (IOException ex) {
	        ex.printStackTrace();
	        return null;
	    }
	    return json;

	}
	public void mCreateAndSaveFile(String params, String mJsonResponse) {
	    try {	    	
	    	FileWriter file = new FileWriter(Environment.getExternalStorageDirectory() + File.separator + params);
	        file.write(mJsonResponse);
	        file.flush();
	        file.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}