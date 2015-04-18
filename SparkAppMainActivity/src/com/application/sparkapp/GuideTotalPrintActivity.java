package com.application.sparkapp;

import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class GuideTotalPrintActivity extends Activity {
	private Utils utils;
	private Bitmap croppedImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_guide_total_print);
		System.gc();
		
		utils = new Utils(this, this);
		RelativeLayout fullGuid = (RelativeLayout) findViewById(R.id.imageGuid);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.spark_guidesummary, utils.getScreenWidth(), utils.getScreenHeight()));
		fullGuid.setBackgroundDrawable(ob);
		
		croppedImage = (Bitmap) getIntent().getParcelableExtra("croppedImage");
		
		fullGuid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserVO user = Entity.query(UserVO.class).where("id=1").execute();
				String tutorial = "";
				if(user!=null){
					tutorial = user.tutorial;
					Intent i = new Intent(GuideTotalPrintActivity.this,ImagePageSummaryActivity.class);
					if("I".equals(tutorial)){
						user.tutorial = "A";
						user.save();
					}
					i.putExtra("croppedImage", croppedImage);
					startActivity(i);
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					finish();
				}else{
					Intent i = new Intent(GuideTotalPrintActivity.this,ImagePageSummaryActivity.class);
					i.putExtra("croppedImage", croppedImage);
					startActivity(i);
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					finish();
				}
			}
		});
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(GuideTotalPrintActivity.this,ImagePageSummaryActivity.class);
		i.putExtra("croppedImage", croppedImage);
		startActivity(i);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		finish();
	}
}
