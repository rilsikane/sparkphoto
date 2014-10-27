package com.application.sparkapp;

import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShippingPageActivity extends Activity {
	private Utils utils;
	private ImageView goToPreviousPage;
	private TextView goToNextPage;
	private EditText email,firstname,lastname,nric,password,cfPassword,phoneno,service,occuption,dob,gender;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_shipping_page);
		System.gc();
		
		utils = new Utils(this, this);
		RelativeLayout fullGuid = (RelativeLayout) findViewById(R.id.imageGuid);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.address_background, utils.getScreenWidth(), utils.getScreenHeight()));
		fullGuid.setBackgroundDrawable(ob);
		
		firstname = (EditText) findViewById(R.id.editText1);
		lastname = (EditText) findViewById(R.id.editText2);
		nric = (EditText) findViewById(R.id.editText3);
		email = (EditText) findViewById(R.id.editText4);
		phoneno = (EditText) findViewById(R.id.editText9);
		service = (EditText) findViewById(R.id.editText10);
		gender = (EditText) findViewById(R.id.editText8);
		dob = (EditText) findViewById(R.id.editText7);
		
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
		UserVO user = Entity.query(UserVO.class).execute();
		if(user!=null){
			firstname.setText(user.firstname);
			lastname.setText(user.lastname);
			nric.setText(user.nric_fin);
			email.setText(user.email);
			phoneno.setText(user.phone);
			service.setText(user.phone_service);
			dob.setText(user.birthday);
		}
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(ShippingPageActivity.this,ImagePageSummaryActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}
	@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}
