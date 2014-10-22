package com.application.sparkapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpPageOneMainActivity extends Activity {

	private Utils utils;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_sign_up_page_one_main);
		System.gc();
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
		final EditText infoIconForNRIC = (EditText) findViewById(R.id.editText3);
		TextView goToNextPage = (TextView) findViewById(R.id.textView2);
		utils = new Utils(getApplicationContext(), this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.signup_background, utils.getScreenWidth(), utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		
		goToNextPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SignUpPageOneMainActivity.this,AddressMainActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();
			}
		});
		infoIconForNRIC.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int DRAWABLE_LEFT = 0;
	            final int DRAWABLE_TOP = 1;
	            final int DRAWABLE_RIGHT = 2;
	            final int DRAWABLE_BOTTOM = 3;
	            if(event.getAction() == MotionEvent.ACTION_UP) {
	                if(event.getRawX() >= (infoIconForNRIC.getRight() - infoIconForNRIC.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
	                    // your action here
	                	Toast.makeText(getApplicationContext(), "Hello Information", Toast.LENGTH_SHORT).show();
	                 return true;
	                }
	            }
	            return false;
			}
		});
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SignUpPageOneMainActivity.this,SparkAppMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
		
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(SignUpPageOneMainActivity.this,SparkAppMainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
		
	}
	

}
