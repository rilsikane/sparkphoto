package com.application.sparkapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

public class PinValidateMainActivity extends Activity {

	private EditText firstPin,secondPin,thirdPin,forthPin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_pin_validate_main);
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
		
		firstPin = (EditText) findViewById(R.id.editText4);
		secondPin = (EditText) findViewById(R.id.editText1);
		thirdPin = (EditText) findViewById(R.id.editText2);
		forthPin = (EditText) findViewById(R.id.editText3);
		
		
		firstPin.addTextChangedListener(new TextWatcher() {		
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(firstPin.getText().length()==0){
					firstPin.requestFocus();
				}else{
					secondPin.requestFocus();
				}
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				// TODO Auto-generated method stub
				
			}
		});
		secondPin.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(secondPin.getText().length()==0){
					firstPin.requestFocus();
				}else{
					thirdPin.requestFocus();
				}
				
			}
		});
		thirdPin.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(thirdPin.getText().length()==0){
					secondPin.requestFocus();
				}else{
					forthPin.requestFocus();	
				}
				
			}
		});
		forthPin.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(forthPin.getText().length()==0){
					thirdPin.requestFocus();
				}
			}
		});
		
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PinValidateMainActivity.this,AddressMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
	}

}
