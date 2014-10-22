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