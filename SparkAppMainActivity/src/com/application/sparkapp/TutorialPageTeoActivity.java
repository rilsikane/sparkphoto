package com.application.sparkapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class TutorialPageTeoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_tutorial_page_teo);
		RelativeLayout sparkTu = (RelativeLayout) findViewById(R.id.sparkTo);
		sparkTu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(TutorialPageTeoActivity.this,MainPhotoSelectActivity.class);
				startActivity(i);
				finish();
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
			}
		});
	}
}
