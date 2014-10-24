package com.application.sparkapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;

public class ActivityNotificationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_activity_notification);
		System.gc();
		ListView activityList = (ListView) findViewById(R.id.listView1);
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ActivityNotificationActivity.this, MainPhotoSelectActivity.class);				 
		        startActivity(i);
		        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		        finish();
			}
		});
		ListAdapter adapter = new ListAdapter();
		activityList.setAdapter(adapter);
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(ActivityNotificationActivity.this, MainPhotoSelectActivity.class);				 
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
	}
	
	public class ListAdapter extends BaseAdapter{
		List<String> temp = new ArrayList<String>();
		public ListAdapter(){
			for(int i =0;i<4;i++){
				temp.add("ORDER00"+(i+1)+" is on its way to your address Thank you for using our service.");
			}
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return temp.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return temp.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.each_layout_activity, null);
			TextView detailTxt = (TextView) convertView.findViewById(R.id.textView1);
			View badgeColor = (View) convertView.findViewById(R.id.view2);
			if(position==1){
				badgeColor.setBackgroundColor(Color.GREEN);
			}else if(position==2){
				badgeColor.setBackgroundColor(Color.RED);
			}
			detailTxt.setText(temp.get(position));
			
			return convertView;
		}
		
	}
}
