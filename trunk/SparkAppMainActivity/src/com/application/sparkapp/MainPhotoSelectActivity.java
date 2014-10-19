package com.application.sparkapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainPhotoSelectActivity extends Activity {
	
	AlertDialog.Builder builder;
	AlertDialog alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main_photo_select);
		ImageView cameraIcon = (ImageView) findViewById(R.id.imageView2);
		ImageView selectIcon = (ImageView) findViewById(R.id.imageView3);
		cameraIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(MainPhotoSelectActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.custom);	
				RelativeLayout closeDialog = (RelativeLayout) dialog.findViewById(R.id.close_dialog_layout);
				closeDialog.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
		selectIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(MainPhotoSelectActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.custom_select_img);	
				RelativeLayout closeDialog = (RelativeLayout) dialog.findViewById(R.id.close_dialog_layout);
				ImageView photoFromSD = (ImageView) dialog.findViewById(R.id.imageView1);
				closeDialog.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				photoFromSD.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(MainPhotoSelectActivity.this,ImageListActivity.class);
						startActivity(i);
						overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					}
				});
				
				dialog.show();
				
			}
		});
		

		
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(MainPhotoSelectActivity.this,SparkAppMainActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}
}
