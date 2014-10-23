package com.application.sparkapp;

import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class EmailLoginActivity extends Activity {
	private Utils utils;
	private EditText email,password;
	private Button btnLogin;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_email_login);
		System.gc();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		utils = new Utils(this, this);
		int screenWidth = utils.getScreenWidth();
        int screenHeight = utils.getScreenHeight();
        btnLogin = (Button) findViewById(R.id.textView2);
        email = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.editText4);
        
        email.setText("test4@gmail.com");
        password.setText("q12345");
		utils = new Utils(getApplicationContext(), this);
        RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
        BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.signup_background, screenWidth, screenHeight));
        root_id.setBackgroundDrawable(ob);
        
        btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserDto user = new UserDto();
				user.setEmail(email.getText().toString());
				user.setPassword(password.getText().toString());
				//CommonDto common = JSONParserForGetList.getInstance().Login(user);
				new InitAndLoadData(user).execute();
			}
		});
	}
	public class InitAndLoadData extends AsyncTask<String, Void, CommonDto> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		UserDto user;
		public InitAndLoadData(UserDto user){
			this.user = user;
		}
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(EmailLoginActivity.this,"Loading ...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected CommonDto doInBackground(String... params) {
			// TODO Auto-generated method stub			
			
			return JSONParserForGetList.getInstance().Login(user);
		}
		
		@Override
		protected void onPostExecute(CommonDto result) {
			super.onPostExecute(result);
			if (result != null) {
				if(result.isFlag()){
					  Intent i = new Intent(EmailLoginActivity.this, TutorialPageOneActivity.class);
					  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	                  startActivity(i);
	                  finish();
				}else{
					final AlertDialog.Builder builder1 = new AlertDialog.Builder(EmailLoginActivity.this);
		            builder1.setMessage("Email and Password are not correctly");
		            builder1.setCancelable(true);
		            builder1.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
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
