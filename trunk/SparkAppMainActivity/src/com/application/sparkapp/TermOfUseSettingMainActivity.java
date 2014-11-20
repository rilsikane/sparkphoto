package com.application.sparkapp;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class TermOfUseSettingMainActivity extends Activity {
	private static String PAGE_FROM = "touLogin";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.settingpage_tou);
		System.gc();
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);	
		
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TermOfUseSettingMainActivity.this,SettingPageActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(TermOfUseSettingMainActivity.this,SettingPageActivity.class);		
		startActivity(intent);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
		
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}
