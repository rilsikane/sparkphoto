package com.application.sparkapp;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.TempUserVO;
import com.application.sparkapp.model.UserVO;
import com.application.sparkapp.util.GlobalVariable;
import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.ORMDroidApplication;

@SuppressLint("NewApi")
public class EmailLoginActivity extends Activity {
	private Utils utils;
	private EditText email,password;
	private Button btnLogin;
	private TextView fogotPswd, registerNow,registerLater;
	private static String PAGE_FROM = "emailLogin";
	private SharedPreferences registerBackPreference;
	private SharedPreferences.Editor backEditor;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_email_login);
		ORMDroidApplication.initialize(EmailLoginActivity.this);
		System.gc();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);		
		
		//Adding preference for handle when back pressed
		registerBackPreference = PreferenceManager.getDefaultSharedPreferences(this);
		backEditor = registerBackPreference.edit();			
		
		utils = new Utils(this, this);
		int screenWidth = utils.getScreenWidth();
        int screenHeight = utils.getScreenHeight();
        btnLogin = (Button) findViewById(R.id.textView2);
        email = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.editText4);
        fogotPswd = (TextView) findViewById(R.id.textView3);
        registerNow = (TextView) findViewById(R.id.textView1);
        registerLater = (TextView) findViewById(R.id.textView4);
        registerNow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				backEditor.putString("BACK_REGISTER_PAGE_STATE", new GlobalVariable().REGISTER_COME_FROM_PAGE_REGIS_NOW);
				backEditor.apply();
				
				Intent i = new Intent(EmailLoginActivity.this, SignUpPageOneMainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
		});
        //registerLater.setVisibility(View.GONE);
        registerLater.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				backEditor.putString("BACK_REGISTER_PAGE_STATE", new GlobalVariable().REGISTER_COME_FROM_PAGE_REGIS_LATER);
				backEditor.apply();
				
				UserVO  user = Entity.query(UserVO.class).execute();
				
				if(user!=null){
					user.delete();
				}
				
				TempUserVO tempUserVO = Entity.query(TempUserVO.class).execute();
				  if(tempUserVO==null){
					  tempUserVO = new TempUserVO();
					  tempUserVO.id = 1;
					  tempUserVO.tutorial = "";
					  tempUserVO.save();
					  Intent i = new Intent(EmailLoginActivity.this, TutorialPageOneActivity.class);
					  i.putExtra("INTENT_FROM", PAGE_FROM);					  
	                  startActivity(i);
//	                  Intent i = new Intent(EmailLoginActivity.this,ShippingAddressActivity.class);
//	  				  startActivity(i);
	                  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	                  finish();
				  }else{
					  if(("D".equals(tempUserVO.tutorial)) || "I".equals(tempUserVO.tutorial)){
						  tempUserVO.id = 1;
						  tempUserVO.save();
						  Intent i = new Intent(EmailLoginActivity.this,TutorialPageOneActivity.class);
						  startActivity(i);
						  finish();
						  overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						  }else{
							  tempUserVO.id = 1;
							  tempUserVO.save();
							  Intent i = new Intent(EmailLoginActivity.this,MainPhotoSelectActivity.class);
							  startActivity(i);
							  finish();
							  overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						  }
				  }
			}
		});
//        email.setText("test@gmail.com");
//        password.setText("123456");
		utils = new Utils(getApplicationContext(), this);
        RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
        BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.login_bg_new, screenWidth, screenHeight));
        root_id.setBackgroundDrawable(ob);
        ImageView goBack = (ImageView) findViewById(R.id.imageView1);
        
        goBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(EmailLoginActivity.this, SparkAppMainActivity.class);				
		        startActivity(i);
		        finish();
		        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		});
        btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserDto user = new UserDto();
				user.setEmail(email.getText().toString());
				user.setPassword(password.getText().toString());
				new InitAndLoadData(user).execute();
				backEditor.clear().commit();
			}
		});
        fogotPswd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  Intent i = new Intent(EmailLoginActivity.this,ForgotActivity.class);
				  startActivity(i);
				  finish();
				  overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		});
	}
	public class InitAndLoadData extends AsyncTask<String, Void, UserDto> implements OnCancelListener{
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
		protected UserDto doInBackground(String... params) {
			// TODO Auto-generated method stub			
			
			return JSONParserForGetList.getInstance().Login(user);
		}
		
		@Override
		protected void onPostExecute(UserDto result) {
			super.onPostExecute(result);
			if (result != null) {
					 try {
						 
					  UserVO user = Entity.query(UserVO.class).where("id=1").execute();
					  if(user==null){
						  user = new UserVO();
						  user = user.convertDtoToVo(result);
						  user.id = 1;
						  user.tutorial = "";
						  user.status = "A";
						  user.save();
						  Intent i = new Intent(EmailLoginActivity.this, TutorialPageOneActivity.class);
						  i.putExtra("INTENT_FROM", PAGE_FROM);					  
		                  startActivity(i);
//		                  Intent i = new Intent(EmailLoginActivity.this,ShippingAddressActivity.class);
//		  				  startActivity(i);
		                  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		                  finish();
					  }else{
						  if(("D".equals(user.tutorial)) || "I".equals(user.tutorial)){
							  user = user.convertDtoToVo(result);
							  user.id = 1;
							  user.status = "A";
							  user.save();
							  Intent i = new Intent(EmailLoginActivity.this,TutorialPageOneActivity.class);
							  startActivity(i);
							  finish();
							  overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
							  mProgressHUD.dismiss();
							  }else{
								  user = user.convertDtoToVo(result);
								  user.id = 1;
								  user.status = "A";
								  user.save();
								  Intent i = new Intent(EmailLoginActivity.this,MainPhotoSelectActivity.class);
								  startActivity(i);
								  finish();
								  overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
								  mProgressHUD.dismiss();
							  }
					  }
					
					 } catch (Exception e) {
						e.printStackTrace();
					}
			} else {
				final AlertDialog.Builder builder1 = new AlertDialog.Builder(EmailLoginActivity.this);
	            builder1.setMessage("Oops! Your email and password don’t match. Please try again");
	            builder1.setCancelable(true);
	            builder1.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	                }
	            });
	            AlertDialog alert11 = builder1.create();
	            alert11.show();
				mProgressHUD.dismiss();
			}
			
		}
		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}


	}
	
	
	@Override
	public void onBackPressed(){
//		Intent i = new Intent(EmailLoginActivity.this, SparkAppMainActivity.class);		
//        startActivity(i);
//        finish();
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		EmailLoginActivity.this.finish();
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}
