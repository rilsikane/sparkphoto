package com.application.sparkapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AboutUsActivity extends Activity {
	private Utils utils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_about_us);
		System.gc();
		utils = new Utils(this, this);
		utils = new Utils(getApplicationContext(), this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.setting_page, utils.getScreenWidth(), utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		
		ImageView goBack = (ImageView) findViewById(R.id.imageView1);
		goBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AboutUsActivity.this, SettingPageActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
		});
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(AboutUsActivity.this, SettingPageActivity.class);				 
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
	}
}
