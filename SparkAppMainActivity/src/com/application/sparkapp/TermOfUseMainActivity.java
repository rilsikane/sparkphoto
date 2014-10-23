package com.application.sparkapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class TermOfUseMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_term_of_use_main);
		System.gc();
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
		ImageView touImage = (ImageView) findViewById(R.id.imageView2);
		TextView accept = (TextView) findViewById(R.id.textView2);
		
		accept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(TermOfUseMainActivity.this,TutorialPageOneActivity.class);
				startActivity(i);
				finish();
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
			}
		});
		touImage.getLayoutParams().height = (new Utils(getApplicationContext(),this).getScreenHeight()*70)/100;
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TermOfUseMainActivity.this,PinValidateMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(TermOfUseMainActivity.this,PinValidateMainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
		
	}
}
