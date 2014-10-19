package com.application.sparkapp;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class ImageCropActivity extends Activity {
	private Utils utils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image_crop);
		utils = new Utils(this, this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.fullBack);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.signup_background, utils.getScreenWidth(), utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
	}


}
