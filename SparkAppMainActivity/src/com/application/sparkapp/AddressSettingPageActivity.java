package com.application.sparkapp;

import com.application.sparkapp.AddressMainActivity.InitAndLoadData;
import com.application.sparkapp.ProfilePageActivity.EditProfileData;
import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
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
	private UserDto userDto;
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
		
		UserVO user = Entity.query(UserVO.class).where("id").eq(1).execute();
		UserDto common = JSONParserForGetList.getInstance().getUserStatus(user.ac_token);
		common.setAccess_token(user.ac_token);
		
		if(common!=null){
			address_block.setText(common.getAddress_block());
			address_street_name.setText(common.getAddress_street_name());
			if(utils.isNotEmpty(common.getAddress_unit_number())){
				String[] units = common.getAddress_unit_number().split("-");
				if(units.length>1){
					address_unit_number1.setText(units[0]);
					address_unit_number2.setText(units[1]);
				}
			}
			address_postal.setText(common.getAddress_postal());
			userDto= common;
		}
		
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
					userDto.setAddress_block(address_block.getText().toString());
					userDto.setAddress_street_name(address_street_name.getText().toString());
					if(Utils.isNotEmpty(address_unit_number1.getText().toString()) && Utils.isNotEmpty(address_unit_number2.getText().toString())){
					String unitNumber = address_unit_number1.getText().toString()+"-"+address_unit_number2.getText().toString();
					userDto.setAddress_unit_number(Utils.isNotEmpty(unitNumber)?unitNumber:"");
					}
					userDto.setAddress_postal(address_postal.getText().toString());
					new EditProfileData().execute();
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

	public class EditProfileData extends AsyncTask<String, Void, CommonDto>
			implements OnCancelListener {
		ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(AddressSettingPageActivity.this,
					"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected CommonDto doInBackground(String... params) {
			// TODO Auto-generated method stub
			CommonDto common = JSONParserForGetList.getInstance().EditProfile(
					userDto, "debug");
			return common;
		}

		@Override
		protected void onPostExecute(CommonDto result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.isFlag()) {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							AddressSettingPageActivity.this);
					builder1.setMessage("Edit Address Completed");
					builder1.setCancelable(true);
					builder1.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									// By pass to term of use main activity
									Intent i = new Intent(
											AddressSettingPageActivity.this,
											SettingPageActivity.class);
									startActivity(i);
									finish();
									overridePendingTransition(
											R.anim.slide_in_left,
											R.anim.slide_out_left);
								}
							});
					AlertDialog alert11 = builder1.create();
					alert11.show();

				} else {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							AddressSettingPageActivity.this);

					String[] msgs = result.getMsg().replaceAll("\\[", "")
							.replaceAll("\\]", "").split(",");
					if (msgs != null && msgs.length > 0) {
						String msg = "Error Please try again "
								+ System.getProperty("line.separator");
						if (msgs != null && msgs.length > 0) {
							for (String ms : msgs) {
								msg += ("-" + ms + System
										.getProperty("line.separator"));
							}

						}
						builder1.setMessage(msg);
					}
					builder1.setCancelable(true);
					builder1.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
					AlertDialog alert11 = builder1.create();
					alert11.show();
				}
				mProgressHUD.dismiss();
			} else {
				mProgressHUD.dismiss();
			}

		}

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}

	}
}
