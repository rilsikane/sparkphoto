package com.application.sparkapp;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FaqActivity extends Activity {
	private Utils utils;
	private TextView faqNo1,faqNo2,faqNo3,faqNo4,faqNo5,faqNo6,faqNo7,faqNo8;
	private TextView ansNo1,ansNo2,ansNo3,ansNo4,ansNo5,ansNo6,ansNo7,ansNo8;
	private boolean isClickNo1,isClickNo2,isClickNo3,isClickNo4,isClickNo5,isClickNo6,isClickNo7,isClickNo8;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_faq);
		System.gc();
		utils = new Utils(this, this);
		utils = new Utils(getApplicationContext(), this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.setting_page, utils.getScreenWidth(), utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		final Typeface face = Typeface.createFromAsset(getAssets(),"fonts/ThaiSansNeue-Bold.ttf");
		final Typeface face2 = Typeface.createFromAsset(getAssets(),"fonts/ThaiSansNeue-Regular.ttf");
		//faq no 1
		isClickNo1 = false;		
		faqNo1 = (TextView) findViewById(R.id.textView3);
		ansNo1 = (TextView) findViewById(R.id.textView4);
		faqNo1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isClickNo1){
					ansNo1.setVisibility(View.VISIBLE);
					faqNo1.setTypeface(face);
					isClickNo1 = true;
				}else{
					ansNo1.setVisibility(View.GONE);
					isClickNo1 = false;
					faqNo1.setTypeface(face2);
				}
				
				
			}
		});
		//faq no 2
		isClickNo2 = false;		
		faqNo2 = (TextView) findViewById(R.id.textView5);
		ansNo2 = (TextView) findViewById(R.id.textView6);
		faqNo2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isClickNo2){
					ansNo2.setVisibility(View.VISIBLE);
					faqNo2.setTypeface(face);
					isClickNo2 = true;
				}else{
					ansNo2.setVisibility(View.GONE);
					isClickNo2 = false;
					faqNo2.setTypeface(face2);
				}
				
				
			}
		});
		//faq no 3
		isClickNo3 = false;		
		faqNo3 = (TextView) findViewById(R.id.textView7);
		ansNo3 = (TextView) findViewById(R.id.textView8);
		faqNo3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isClickNo3){
					ansNo3.setVisibility(View.VISIBLE);
					faqNo3.setTypeface(face);
					isClickNo3 = true;
				}else{
					ansNo3.setVisibility(View.GONE);
					isClickNo3 = false;
					faqNo3.setTypeface(face2);
				}
				
				
			}
		});
		//faq no 4
		isClickNo4 = false;		
		faqNo4 = (TextView) findViewById(R.id.textView9);
		ansNo4 = (TextView) findViewById(R.id.textView10);
		faqNo4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isClickNo4){
					ansNo4.setVisibility(View.VISIBLE);
					faqNo4.setTypeface(face);
					isClickNo4 = true;
				}else{
					ansNo4.setVisibility(View.GONE);
					isClickNo4 = false;
					faqNo4.setTypeface(face2);
				}
				
				
			}
		});
		//faq no 5
		isClickNo5 = false;		
		faqNo5 = (TextView) findViewById(R.id.textView11);
		ansNo5 = (TextView) findViewById(R.id.textView12);
		faqNo5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isClickNo5){
					ansNo5.setVisibility(View.VISIBLE);
					faqNo5.setTypeface(face);
					isClickNo5 = true;
				}else{
					ansNo5.setVisibility(View.GONE);
					isClickNo5 = false;
					faqNo5.setTypeface(face2);
				}
				
				
			}
		});
		//faq no 6
		isClickNo6 = false;		
		faqNo6 = (TextView) findViewById(R.id.textView13);
		ansNo6 = (TextView) findViewById(R.id.textView14);
		faqNo6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isClickNo6){
					ansNo6.setVisibility(View.VISIBLE);
					faqNo6.setTypeface(face);
					isClickNo6 = true;
				}else{
					ansNo6.setVisibility(View.GONE);
					isClickNo6 = false;
					faqNo6.setTypeface(face2);
				}
				
				
			}
		});
		//faq no 7
		isClickNo7 = false;		
		faqNo7 = (TextView) findViewById(R.id.textView15);
		ansNo7 = (TextView) findViewById(R.id.textView16);
		faqNo7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isClickNo7){
					ansNo7.setVisibility(View.VISIBLE);
					faqNo7.setTypeface(face);
					isClickNo7 = true;
				}else{
					ansNo7.setVisibility(View.GONE);
					isClickNo7 = false;
					faqNo7.setTypeface(face2);
				}
				
				
			}
		});
		//faq no 8
		isClickNo8 = false;		
		faqNo8 = (TextView) findViewById(R.id.textView17);
		ansNo8 = (TextView) findViewById(R.id.textView18);
		faqNo8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isClickNo8){
					ansNo8.setVisibility(View.VISIBLE);
					faqNo8.setTypeface(face);
					isClickNo8= true;
				}else{
					ansNo8.setVisibility(View.GONE);
					isClickNo8 = false;
					faqNo8.setTypeface(face2);
				}
				
				
			}
		});
		ImageView goBack = (ImageView) findViewById(R.id.imageView1);
		goBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(FaqActivity.this, SettingPageActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
		});
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(FaqActivity.this, SettingPageActivity.class);				 
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}
