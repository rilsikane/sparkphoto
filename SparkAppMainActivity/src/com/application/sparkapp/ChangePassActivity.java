package com.application.sparkapp;

import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChangePassActivity extends Activity {
	private Utils utils;
	private ImageView backIcon;
	private TextView comfirmText;
	private UserDto userDto;
	private String newPass;
	private EditText curPass,nwPass,conPass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.activity_change_pass);
		System.gc();
		UserVO user = Entity.query(UserVO.class).where("id").eq(1).execute();
		UserDto common = JSONParserForGetList.getInstance().getUserStatus(user.ac_token);
		common.setAccess_token(user.ac_token);
		
		if(common!=null){
			userDto= common;
		}
		backIcon = (ImageView) findViewById(R.id.imageView1);
		utils = new Utils(this, this);
		utils = new Utils(getApplicationContext(), this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.setting_page, utils.getScreenWidth(), utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		comfirmText = (TextView) findViewById(R.id.textView2);
		
		curPass = (EditText) findViewById(R.id.editText3);
		nwPass = (EditText) findViewById(R.id.editText5);
		conPass = (EditText) findViewById(R.id.editText7);
		
		curPass.addTextChangedListener(new EditTextWatcher(curPass, "Please enter password"));
		nwPass.addTextChangedListener(new EditTextWatcher(nwPass, "Please enter confirm password"));
		conPass.addTextChangedListener(new EditTextWatcher(conPass, "Please enter confirm password"));
		
		comfirmText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (utils.isNotEmpty(curPass.getText().toString())&& utils.isNotEmpty(nwPass.getText().toString())&& utils.isNotEmpty(conPass.getText().toString())){
					if (!conPass.getText().toString().equals(nwPass.getText().toString())) {
						conPass.setError("Password is not match");
					}else{
						userDto.setPassword(curPass.getText().toString());
						newPass =  nwPass.getText().toString();
						new EditProfileData().execute();
					}
					
					
				}else if(utils.isNotEmpty(curPass.getText().toString())|| utils.isNotEmpty(nwPass.getText().toString())|| utils.isNotEmpty(conPass.getText().toString())){
					AlertDialog.Builder builder1 = new AlertDialog.Builder(ChangePassActivity.this);
					builder1.setMessage("Please enter valid information");
					builder1.setCancelable(true);
					builder1.setPositiveButton("OK",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									dialog.cancel();
									
								}
							});
					AlertDialog alert11 = builder1.create();
					alert11.show();
				}else{
					AlertDialog.Builder builder1 = new AlertDialog.Builder(ChangePassActivity.this);
					builder1.setMessage("Password is not change");
					builder1.setCancelable(true);
					builder1.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									Intent i = new Intent(ChangePassActivity.this,ProfilePageActivity.class);
									startActivity(i);
									finish();
									overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
																	
								}
							});
					AlertDialog alert11 = builder1.create();
					alert11.show();
				}
				
			}
		});
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent i = new Intent(ChangePassActivity.this, ProfilePageActivity.class);				 
                 startActivity(i);
                 overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                 finish();
			}
		});
	}
	@Override
	public void onBackPressed(){
		 Intent i = new Intent(ChangePassActivity.this, ProfilePageActivity.class);				 
         startActivity(i);
         overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
         finish();
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
			mProgressHUD = ProgressHUD.show(ChangePassActivity.this,
					"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected CommonDto doInBackground(String... params) {
			// TODO Auto-generated method stub
			CommonDto common = JSONParserForGetList.getInstance().changePassword(
					userDto, "debug",newPass);
			return common;
		}

		@Override
		protected void onPostExecute(CommonDto result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.isFlag()) {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							ChangePassActivity.this);
					builder1.setMessage("Password is Changed");
					builder1.setCancelable(true);
					builder1.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									// By pass to term of use main activity
									Intent i = new Intent(
											ChangePassActivity.this,
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
							ChangePassActivity.this);

					String[] msgs = result.getMsg().replaceAll("\\[", "")
							.replaceAll("\\]", "").split("\\.");
					if (msgs != null && msgs.length > 0) {
						String msg = "Error Please try again "
								+ System.getProperty("line.separator");
						if (msgs != null && msgs.length > 0) {
							for (String ms : msgs) {
								msg += ("-" + ms.replaceFirst(",", "") + System
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
			if(utils.isNotEmpty(_edt.getText().toString())){
				_edt.setError(null);
			}else{
				_edt.setError(_msg);
			}
		}
		
	}
}
