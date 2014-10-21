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
import android.widget.TextView;

public class ShippingPageActivity extends Activity {
	private Utils utils;
	private ImageView goToPreviousPage;
	private TextView goToNextPage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_shipping_page);
		
		utils = new Utils(this, this);
		RelativeLayout fullGuid = (RelativeLayout) findViewById(R.id.imageGuid);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.address_background, utils.getScreenWidth(), utils.getScreenHeight()));
		fullGuid.setBackgroundDrawable(ob);
		
		goToPreviousPage = (ImageView) findViewById(R.id.imageView1);
		goToNextPage = (TextView) findViewById(R.id.textView2);
		goToNextPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ShippingPageActivity.this,ShippingAddressActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();
			}
		});
		goToPreviousPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ShippingPageActivity.this,ImagePageSummaryActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
	}

}
