package com.application.sparkapp;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;
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
	private ViewHolder viewHolder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image_list);
		System.gc();
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
		
		String facebookUserId = "";
		if(getIntent().hasExtra("facebookUserId")){
			facebookUserId = getIntent().getStringExtra("facebookUserId");
		}
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
		            			Log.d("facebook.user.cover.photo", item.getString("cover_photo"));
			            		temp = new TempListContentView();
			                    temp.setAlbumsName(item.getString("name"));
			        	        new Request(Session.getActiveSession(),item.getString("cover_photo")+"/",null,HttpMethod.GET,new Request.Callback() {
			        	        	public void onCompleted(Response response) {
			        	        	        	JSONArray photoCover ;
			        	        	        	try {
													photoCover = response.getGraphObject().getInnerJSONObject().getJSONArray("images");
													JSONObject item = photoCover.getJSONObject(0);
													Log.d("facebook.user.cover.photo.img", item.getString("source"));
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
			listContent = getAlbums();
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
			if(convertView== null){
				viewHolder = new ViewHolder();
				LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
				convertView = inflater.inflate(R.layout.custom_album_list, null);
				
				viewHolder.coverImg = (ImageView) convertView.findViewById(R.id.imageView1);
				viewHolder.albumName = (TextView) convertView.findViewById(R.id.albumName);
				viewHolder.noOfPic = (TextView) convertView.findViewById(R.id.noPicture);
				viewHolder.click = (RelativeLayout) convertView.findViewById(R.id.each_list_layout);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			TempListContentView temps = _list.get(position);
			viewHolder.click.setOnClickListener(new OnSelectImgListener(position,convertView,temps));
			
			viewHolder.albumName.setText(temps.getAlbumsName());
			viewHolder.noOfPic.setText(String.valueOf(temps.getNumberOfImage()));
			if(getIntent().getIntExtra("loadImageState", 0)==0){
				Picasso.with(getApplicationContext()).load(temps.getImgPathUrl()).resize(100, 100).into(viewHolder.coverImg);
			}else if(getIntent().getIntExtra("loadImageState", 0)==1){
				Picasso.with(getApplicationContext()).load(new File(temps.getImgPathUrl())).resize(100, 100).into(viewHolder.coverImg);
			}
				
			
			return convertView;
		}
		
	}
	public class OnSelectImgListener implements OnClickListener{
		private int _position;
		private View view;
		private TempListContentView temps;
		public OnSelectImgListener(int position,View btnView,TempListContentView temps){
			this._position = position;
			this.view = btnView;
			this.temps = temps;
		}
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(ImageListActivity.this,ImageGridViewActivity.class);
			if(getIntent().hasExtra("facebookUserId")){
				i.putExtra("facebookUserId", getIntent().getStringExtra("facebookUserId"));
			}
			i.putStringArrayListExtra("imgList", temps.getImgList());
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		}
		
	}
	public class ViewHolder{
		public TextView albumName,noOfPic;
		public ImageView coverImg;
		public RelativeLayout click;
	}
	public class TempListContentView{
		
		private String albumsName;
		private int numberOfImage;
		private String imgPathUrl;
		private int imgDrawable;
		private int albumId;
		private ArrayList<String> imgList;
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
		public int getAlbumId() {
			return albumId;
		}
		public void setAlbumId(int albumId) {
			this.albumId = albumId;
		}
		public ArrayList<String> getImgList() {
			return imgList;
		}
		public void setImgList(ArrayList<String> imgList) {
			this.imgList = imgList;
		}
		
		
		
	}
	public ArrayList<TempListContentView> getAlbums() {
		// which image properties are we querying
		ArrayList<TempListContentView> tempList = new ArrayList<TempListContentView>();
		String[] PROJECTION_BUCKET = {
	            ImageColumns.BUCKET_ID,
	            ImageColumns.BUCKET_DISPLAY_NAME,
	            ImageColumns.DATE_TAKEN,
	            ImageColumns.DATA};
	  
	    String BUCKET_GROUP_BY =
	            "1) GROUP BY 1,(2";
	    String BUCKET_ORDER_BY = "MAX(datetaken) DESC";

	    // Get the base URI for the People table in the Contacts content provider.
	    Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

	    Cursor cur = getContentResolver().query(
	            images, PROJECTION_BUCKET, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);

	    Log.i("ListingImages"," query count=" + cur.getCount());

	    if (cur.moveToFirst()) {
	        String bucket;
	        String date;
	        String data;
	        int bucketColumn = cur.getColumnIndex(
	                MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

	        int dateColumn = cur.getColumnIndex(
	                MediaStore.Images.Media.DATE_TAKEN);
	        int dataColumn = cur.getColumnIndex(
	                MediaStore.Images.Media.DATA);

	        do {
	        	TempListContentView temp = new TempListContentView();
	            bucket = cur.getString(bucketColumn);
	            date = cur.getString(dateColumn);
	            data = cur.getString(dataColumn);
	            temp.setAlbumsName(bucket);
	            temp.setImgPathUrl(data);
	            
	            String parent = data.substring(0,data.lastIndexOf('/'));
	            ArrayList<String> imgList = findImgChild(parent);
	            temp.setImgList(imgList);
	            temp.setNumberOfImage(imgList.size());
	            tempList.add(temp);
	            // Do something with the values.
	            Log.i("ListingImages", " bucket=" + bucket 
	                    + "  date_taken=" + date
	                    + "  _data=" + data);
	        } while (cur.moveToNext());
	    }

		  return tempList;
	}
	private ArrayList<String> findImgChild(String path){
		ArrayList<String> imgList = new ArrayList<String>();
		File file = new File(path);

		File imageList[] = file.listFiles();

		 for(int i=0;i<imageList.length;i++)
		 {
			String filename = imageList[i].getAbsolutePath();
			if(checkImage(filename)){
				imgList.add(filename);
			}

		 }
		 
		 return imgList;
	}
	public boolean checkImage(String filename){
		boolean isImage = false;
		String type = "";
		if(filename!=null && !"".equals(filename)){
			if((filename.lastIndexOf(".")>0) && filename.lastIndexOf(".") < filename.length()){
				type = filename.substring(filename.lastIndexOf("."));
			}
			
			if(".jpg".equals(type.toLowerCase()) || ".png".equals(type.toLowerCase()) || ".jpeg".equals(type.toLowerCase())){
				isImage = true;
			}
		}
		return isImage;
	}

}
