package com.application.sparkapp;

import java.util.ArrayList;

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

public class ImageListActivity extends Activity {

	private TempListContentView temp;
	private ArrayList<TempListContentView> listContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image_list);
		ImageView backToPrevious = (ImageView) findViewById(R.id.imageView1);
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
		
		ListView lv = (ListView) findViewById(R.id.listView1);
		listContent = new ArrayList<TempListContentView>();
		temp = new TempListContentView();
		temp.setAlbumsName("Camera Roll");
		temp.setNumberOfImage(602);
		listContent.add(temp);
		LoadListAdapter adapter = new LoadListAdapter();
		lv.setAdapter(adapter);
		
	}
	public class LoadListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listContent.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listContent.get(position);
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
			TempListContentView temps = listContent.get(position);
			TextView albumName = (TextView) convertView.findViewById(R.id.albumName);
			TextView noOfPic = (TextView) convertView.findViewById(R.id.noPicture);
			RelativeLayout click = (RelativeLayout) convertView.findViewById(R.id.each_list_layout);
			
			click.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(ImageListActivity.this,ImageGridViewActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				}
			});
			
			albumName.setText(temps.getAlbumsName());
			noOfPic.setText(String.valueOf(temps.getNumberOfImage()));
			
			return convertView;
		}
		
	}
	public class TempListContentView{
		private String albumsName;
		private int numberOfImage;
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
		
		
	}

}
