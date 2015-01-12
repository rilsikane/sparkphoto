package com.application.sparkapp;

import org.w3c.dom.Text;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.facebook.Session;
import com.roscopeco.ormdroid.Entity;

@SuppressLint("NewApi")
public class ForgotActivity extends Activity {
	private Utils utils;
	private EditText email,password;
	private TextView btnSend;
	private static String PAGE_FROM = "emailLogin";
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_forgot_pwd);
		System.gc();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		utils = new Utils(this, this);
		int screenWidth = utils.getScreenWidth();
        int screenHeight = utils.getScreenHeight();
        btnSend = (TextView) findViewById(R.id.textView2);
        email = (EditText) findViewById(R.id.editText3);
        
//        email.setText("test@gmail.com");
//        password.setText("123456");
		utils = new Utils(getApplicationContext(), this);
        RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
        BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.signup_background, screenWidth, screenHeight));
        root_id.setBackgroundDrawable(ob);
        ImageView goBack = (ImageView) findViewById(R.id.imageView1);
        
        goBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ForgotActivity.this, EmailLoginActivity.class);				
		        startActivity(i);
		        finish();
		        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		});
        btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserDto user = new UserDto();
				String emailTxt = email.getText().toString();
				new InitAndLoadData(emailTxt).execute();
			}
		});
	}
	public class InitAndLoadData extends AsyncTask<String, Void, CommonDto> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		String emailTxt;
		public InitAndLoadData(String emailTxt){
			this.emailTxt = emailTxt;
		}
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(ForgotActivity.this,"Loading ...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected CommonDto doInBackground(String... params) {
			// TODO Auto-generated method stub			
			
			return JSONParserForGetList.getInstance().forgotPswd(emailTxt);
		}
		
		@Override
		protected void onPostExecute(CommonDto result) {
			super.onPostExecute(result);
			if (result != null) {
					 try {
						 final AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgotActivity.this);
				            builder1.setCancelable(true);
				            builder1.setMessage("Success!");
				            builder1.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
				                public void onClick(DialogInterface dialog, int id) {
				                    dialog.cancel();
				                    Intent i = new Intent(ForgotActivity.this, EmailLoginActivity.class);				
				    		        startActivity(i);
				    		        finish();
				    		        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				                }
				            });
				            AlertDialog alert11 = builder1.create();
				            alert11.show();
							mProgressHUD.dismiss();
					
					  }catch (Exception e) {
						e.printStackTrace();
					}
			} else if(Utils.isNotEmpty(result.getMsg())) {
				final AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgotActivity.this);
				String[] msgs = result.getMsg().replaceAll("\\[", "")
						.replaceAll("\\]", "").split("\\.");
				if (msgs != null && msgs.length > 0) {
					String msg = "Error: please try again. "
							+ System.getProperty("line.separator");
					if (msgs != null && msgs.length > 0) {
						msg += "- ";
						for (String ms : msgs) {
							msg += ms.replaceFirst(",", "");
						}

					}
					builder1.setMessage(msg);
				}
				
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
	public class ForgotPassword extends AsyncTask<String, Void, CommonDto> implements OnCancelListener{
		ProgressHUD mProgressHUD;
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(ForgotActivity.this,"Loading ...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected CommonDto doInBackground(String... params) {
			// TODO Auto-generated method stub			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(CommonDto result) {
			super.onPostExecute(result);
			if (result != null) {
				
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
		Intent i = new Intent(ForgotActivity.this, SparkAppMainActivity.class);		
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}
