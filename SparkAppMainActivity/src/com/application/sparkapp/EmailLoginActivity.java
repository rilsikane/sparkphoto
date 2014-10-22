package com.application.sparkapp;

import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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
		utils = new Utils(this, this);
		int screenWidth = utils.getScreenWidth();
        int screenHeight = utils.getScreenHeight();
        btnLogin = (Button) findViewById(R.id.textView2);
        email = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.editText4);
        
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
				CommonDto common = JSONParserForGetList.getInstance().Login(user);
				if(common.isFlag()){
					  Intent i = new Intent(EmailLoginActivity.this, MainPhotoSelectActivity.class);
	                  startActivity(i);
				}else{
					final AlertDialog.Builder builder1 = new AlertDialog.Builder(EmailLoginActivity.this);
		            builder1.setMessage("Email and Password are not correctly");
		            builder1.setCancelable(true);
		            builder1.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		                    AlertDialog alert11 = builder1.create();
				            alert11.show();
		                }
		            });
				}
			}
		});
	}
}
