package com.application.sparkapp;

import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ImageGuidCropActivity extends Activity {
	private Utils utils;
	private String imgPath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image_guid_crop);
		System.gc();
		utils = new Utils(this, this);
		RelativeLayout fullGuid = (RelativeLayout) findViewById(R.id.imageGuid);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.guid_crop_background, utils.getScreenWidth(), utils.getScreenHeight()));
		fullGuid.setBackgroundDrawable(ob);
		imgPath = getIntent().getStringExtra("imgPath");
		
		fullGuid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
					Intent i = new Intent(ImageGuidCropActivity.this,ImageCropActivity.class);
					i.putExtra("imgPath", imgPath);
					i.putExtra("isFacebook", getIntent().getBooleanExtra("isFacebook", false));
					i.putStringArrayListExtra("IMG_LIST", getIntent().getStringArrayListExtra("IMG_LIST"));
					startActivity(i);
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					finish();
				
				
				
			}
		});
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(ImageGuidCropActivity.this,ImageCropActivity.class);
		i.putExtra("imgPath", imgPath);
		i.putStringArrayListExtra("IMG_LIST", getIntent().getStringArrayListExtra("IMG_LIST"));
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}

}
