package com.application.sparkapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.squareup.picasso.Picasso;

public class ImageListActivity extends Activity {

	private TempListContentView temp;
	ArrayList<TempListContentView> listContent;
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image_list);
		ImageView backToPrevious = (ImageView) findViewById(R.id.imageView1);
		lv = (ListView) findViewById(R.id.listView1);
		backToPrevious.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ImageListActivity.this,MainPhotoSelectActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
		
		String facebookUserId = getIntent().getStringExtra("facebookUserId");
        //Check isFacebook
		if(Session.getActiveSession()!=null){
			
            new Request(Session.getActiveSession(),facebookUserId+"/albums",null,HttpMethod.GET,new Request.Callback() {
        	public void onCompleted(Response response) {
        		JSONArray albumArr;
        			listContent = new ArrayList<TempListContentView>();
					try {
						albumArr = response.getGraphObject().getInnerJSONObject().getJSONArray("data");
						for (int i = 0; i < albumArr.length(); i++) {
		                    JSONObject item = albumArr.getJSONObject(i);
		            		if(checkEmptyCount(item)){
			            		temp = new TempListContentView();
			                    temp.setAlbumsName(item.getString("name"));
			        	        new Request(Session.getActiveSession(),item.getString("cover_photo")+"/",null,HttpMethod.GET,new Request.Callback() {
			        	        	public void onCompleted(Response response) {
			        	        	        	JSONArray photoCover ;
			        	        	        	try {
													photoCover = response.getGraphObject().getInnerJSONObject().getJSONArray("images");
													JSONObject item = photoCover.getJSONObject(0);
													temp.setImgPathUrl(item.getString("source"));
												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
			        	        	        	
			        	        	        }
			        	        	    }
			        	        ).executeAsync();	                    
			                    temp.setNumberOfImage(Integer.parseInt(item.getString("count")));
			            		listContent.add(temp);
		            		}
		                }
						LoadListAdapter adapter = new LoadListAdapter(listContent);
						lv.setAdapter(adapter);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

        	    }
        	}).executeAsync();
		}else{
			//Normal Photo select
			listContent = new ArrayList<TempListContentView>();
			TempListContentView temp = new TempListContentView();
			temp.setAlbumsName("Camera Roll");
			temp.setNumberOfImage(200);
			listContent.add(temp);
			LoadListAdapter adapter = new LoadListAdapter(listContent);
			lv.setAdapter(adapter);
		}
		
		
	}
	public boolean checkEmptyCount(JSONObject item){
		try {
        	item.get("count");
    		return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public class LoadListAdapter extends BaseAdapter{
		public ArrayList<TempListContentView> _list;
		public LoadListAdapter(ArrayList<TempListContentView> list){
			this._list = list;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return _list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return _list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = inflater.inflate(R.layout.custom_album_list, null);
			TempListContentView temps = _list.get(position);
			ImageView coverImage = (ImageView) convertView.findViewById(R.id.imageView1);
			TextView albumName = (TextView) convertView.findViewById(R.id.albumName);
			TextView noOfPic = (TextView) convertView.findViewById(R.id.noPicture);
			RelativeLayout click = (RelativeLayout) convertView.findViewById(R.id.each_list_layout);
			
			
			
			click.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(ImageListActivity.this,ImageGridViewActivity.class);
					i.putExtra("facebookUserId", getIntent().getStringExtra("facebookUserId"));
					startActivity(i);
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				}
			});
			
			albumName.setText(temps.getAlbumsName());
			noOfPic.setText(String.valueOf(temps.getNumberOfImage()));
			Picasso.with(getApplicationContext()).load(temps.getImgPathUrl()).into(coverImage);
			
			return convertView;
		}
		
	}
	public class TempListContentView{
		private String albumsName;
		private int numberOfImage;
		private String imgPathUrl;
		private int imgDrawable;
		public String getAlbumsName() {
			return albumsName;
		}
		public void setAlbumsName(String albumsName) {
			this.albumsName = albumsName;
		}
		public int getNumberOfImage() {
			return numberOfImage;
		}
		public void setNumberOfImage(int numberOfImage) {
			this.numberOfImage = numberOfImage;
		}
		public String getImgPathUrl() {
			return imgPathUrl;
		}
		public void setImgPathUrl(String imgPathUrl) {
			this.imgPathUrl = imgPathUrl;
		}
		public int getImgDrawable() {
			return imgDrawable;
		}
		public void setImgDrawable(int imgDrawable) {
			this.imgDrawable = imgDrawable;
		}
		
		
	}

}
