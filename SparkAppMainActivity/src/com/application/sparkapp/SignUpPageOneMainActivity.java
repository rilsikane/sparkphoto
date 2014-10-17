package com.application.sparkapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignUpPageOneMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_sign_up_page_one_main);
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
		final EditText infoIconForNRIC = (EditText) findViewById(R.id.editText3);
		
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
