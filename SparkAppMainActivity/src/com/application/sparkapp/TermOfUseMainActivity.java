package com.application.sparkapp;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;

public class TermOfUseMainActivity extends Activity {
	private static String PAGE_FROM = "touLogin";
	private UserDto userDto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_term_of_use_main);
		System.gc();
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
		ImageView touImage = (ImageView) findViewById(R.id.imageView2);
		TextView accept = (TextView) findViewById(R.id.textView2);
		userDto = getIntent().getExtras().getParcelable("userDto");
		
		accept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userDto.setConfrim(true);
				new InitAndLoadData().execute();
				
			}
		});
		touImage.getLayoutParams().height = (new Utils(getApplicationContext(),this).getScreenHeight()*70)/100;
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TermOfUseMainActivity.this,PinValidateMainActivity.class);
				intent.putExtra("userDto", (Parcelable) userDto);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(TermOfUseMainActivity.this,PinValidateMainActivity.class);
		intent.putExtra("userDto", (Parcelable) userDto);
		startActivity(intent);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
		
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

	public class InitAndLoadData extends AsyncTask<String, Void, CommonDto>
			implements OnCancelListener {
		ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(TermOfUseMainActivity.this,
					"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected CommonDto doInBackground(String... params) {
			// TODO Auto-generated method stub
			CommonDto common = JSONParserForGetList.getInstance().Register(
					userDto);
			return common;
		}

		@Override
		protected void onPostExecute(CommonDto result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.isFlag()) {
					
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							TermOfUseMainActivity.this);
					builder1.setMessage("Registration complete");
					builder1.setCancelable(true);
					builder1.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									UserDto dto = JSONParserForGetList.getInstance().Login(userDto);
									dialog.cancel();
									  UserVO user = new UserVO();
									  user = user.convertDtoToVo(dto);
									  user.id = 1;
									  user.tutorial = "";
									  user.status = "A";
									  user.save();
//					                  Intent i = new Intent(EmailLoginActivity.this,ShippingAddressActivity.class);
//					  				  startActivity(i);
					                  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					                  SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					          		SharedPreferences.Editor editor = settings.edit();
					          		editor.putInt("resendTime", 0);
					          		editor.commit();
					                  finish();
									Intent i = new Intent(TermOfUseMainActivity.this,TutorialPageOneActivity.class);
									i.putExtra("INTENT_FROM", PAGE_FROM);
									startActivity(i);
									finish();
									overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
								}
							});
					AlertDialog alert11 = builder1.create();
					alert11.show();

				} else {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							TermOfUseMainActivity.this);

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

}
