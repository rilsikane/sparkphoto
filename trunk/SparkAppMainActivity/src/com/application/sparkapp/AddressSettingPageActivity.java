package com.application.sparkapp;

import com.application.sparkapp.AddressMainActivity.InitAndLoadData;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi") public class AddressSettingPageActivity extends Activity {
	private EditText address_block;
	private EditText address_street_name;
	private EditText address_unit_number1,address_unit_number2;
	private EditText address_postal;
	private Utils utils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf",R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_address_setting_page);
		System.gc();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
		TextView confirm = (TextView) findViewById(R.id.textView2);
		address_block = (EditText) findViewById(R.id.editText3);
		address_street_name = (EditText) findViewById(R.id.editText4);
		address_unit_number1 = (EditText) findViewById(R.id.editText7);
		address_unit_number2 = (EditText) findViewById(R.id.EditText01);
		address_postal = (EditText) findViewById(R.id.editText8);
		utils = new Utils(this, this);
		
		address_unit_number1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
		address_unit_number1.addTextChangedListener(new TextWatcher() {		
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(address_unit_number1.getText().length()==0){
					address_unit_number1.requestFocus();
				}else if(address_unit_number1.getText().length()==2){
					address_unit_number2.requestFocus();
				}else if(address_unit_number1.getText().length()>2){
					address_unit_number2.requestFocus();
				}else{
					address_unit_number1.requestFocus();
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
		address_unit_number2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});
		address_unit_number2.addTextChangedListener(new TextWatcher() {		
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(address_unit_number2.getText().length()==0){
					address_unit_number1.requestFocus();
				}else if(address_unit_number2.getText().length()==3){
					address_unit_number2.requestFocus();
				}else if(address_unit_number2.getText().length()==2){
					address_unit_number2.requestFocus();
				}else if(address_unit_number2.getText().length()==1){
					address_unit_number2.requestFocus();
				}else{
					address_unit_number2.requestFocus();
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
		
		address_street_name.addTextChangedListener(new EditTextWatcher(address_street_name, "Please enter Street name"));
		address_postal.addTextChangedListener(new EditTextWatcher(address_postal, "Please enter Postal code"));
		
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Utils.isNotEmpty(address_street_name.getText().toString())&& Utils.isNotEmpty(address_postal.getText().toString())) {
					
					
				}else if(address_street_name.getText().toString().isEmpty()){
					address_street_name.setError("Please enter Street name");
				}else if(address_postal.getText().toString().isEmpty()){
					address_postal.setError("Please enter Postal code");
				}
				

			}
		});
		
		backIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AddressSettingPageActivity.this,SettingPageActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed(){
		Intent intent = new Intent(AddressSettingPageActivity.this,SettingPageActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}
	public class EditTextWatcher implements TextWatcher{
		public EditText _edt;
		public String _msg;
		
		public EditTextWatcher(EditText edT, String msg){
			this._edt= edT;
			this._msg = msg;
		}
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
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
			if(Utils.isNotEmpty(_edt.getText().toString())){
				_edt.setError(null);
			}else{
				_edt.setError(_msg);
			}
		}
		
	}
	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(new CalligraphyContextWrapper(newBase));
	}
}
