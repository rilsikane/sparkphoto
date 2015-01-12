package com.application.sparkapp;

import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.util.GlobalState;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PinValidateMainActivity extends Activity {

	private EditText firstPin,secondPin,thirdPin,forthPin;
	private UserDto userDto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_pin_validate_main);
		System.gc();
		new Utils(this,this).setupUI(findViewById(R.id.root_id));
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
		TextView goToNextPage = (TextView) findViewById(R.id.textView2);
		TextView resend = (TextView) findViewById(R.id.textView5);
		
		firstPin = (EditText) findViewById(R.id.editText4);
		secondPin = (EditText) findViewById(R.id.editText1);
		thirdPin = (EditText) findViewById(R.id.editText2);
		forthPin = (EditText) findViewById(R.id.editText3);
		userDto = getIntent().getExtras().getParcelable("userDto");
		
		
		
		goToNextPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Utils.isNotEmpty(firstPin.getText().toString()) && Utils.isNotEmpty(secondPin.getText().toString())
						&&Utils.isNotEmpty(thirdPin.getText().toString()) && Utils.isNotEmpty(forthPin.getText().toString())){
					String pin = firstPin.getText().toString()+secondPin.getText().toString()+thirdPin.getText().toString()+forthPin.getText().toString();
					userDto.setUser_validateCode(pin);
					new InitAndLoadData().execute();
				}else{
					AlertDialog.Builder builder1 = new AlertDialog.Builder(PinValidateMainActivity.this);					
					builder1.setMessage("Please enter valid PIN number");				
					builder1.setCancelable(true);
					builder1.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									dialog.cancel();
								}
							});
					AlertDialog alert11 = builder1.create();
					alert11.show();
				}
			}
		});
		
		resend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(PinValidateMainActivity.this);
				int time = prefs.getInt("resendTime", 0);
				if(time<5){
					new ResendLoadData().execute();
					SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					SharedPreferences.Editor editor = settings.edit();
					editor.putInt("resendTime", time+1);
					editor.commit();
				}else{
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							PinValidateMainActivity.this);

					builder1.setMessage("An error occur, Please try a again later.");
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
			}
		});
		
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
				Intent intent = new Intent(PinValidateMainActivity.this,SignUpPageOneMainActivity.class);
//				intent.putExtra("userDto", (Parcelable) userDto);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed() {
//		Intent intent = new Intent(PinValidateMainActivity.this,AddressMainActivity.class);
//		intent.putExtra("userDto", (Parcelable) userDto);
//		startActivity(intent);
//		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//		finish();
		
	}

	public class InitAndLoadData extends AsyncTask<String, Void, CommonDto>
			implements OnCancelListener {
		ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(PinValidateMainActivity.this,
					"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected CommonDto doInBackground(String... params) {
			// TODO Auto-generated method stub
			CommonDto common = JSONParserForGetList.getInstance().Register2(
					userDto);
			return common;
		}

		@Override
		protected void onPostExecute(CommonDto result) {
			super.onPostExecute(result);
			if (result != null && Utils.isNotEmpty(result.getMsg())) {
				String[] msgs = result.getMsg().replaceAll("\\[", "")
						.replaceAll("\\]", "").split("\\.");
				if (contains(msgs, "term")) {
						Intent intent = new Intent(PinValidateMainActivity.this,
								TermOfUseMainActivity.class);
						intent.putExtra("userDto", (Parcelable) userDto);
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_left,
								R.anim.slide_out_left);
						finish();

				} else {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							PinValidateMainActivity.this);
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

	public class ResendLoadData extends AsyncTask<String, Void, CommonDto>
			implements OnCancelListener {
		ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(PinValidateMainActivity.this,
					"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected CommonDto doInBackground(String... params) {
			// TODO Auto-generated method stub
			CommonDto common = JSONParserForGetList.getInstance().getOTP(
					userDto);
			return common;
		}

		@Override
		protected void onPostExecute(CommonDto result) {
			super.onPostExecute(result);
			if (result != null) {
				if (!result.isFlag()) {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							PinValidateMainActivity.this);
					String[] msgs = result.getMsg().replaceAll("\\[", "")
							.replaceAll("\\]", "").split("\\.");
					if (msgs != null && msgs.length > 0) {
						String msg = "Error: please try again. "
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
				}else{
					userDto.setOtp_token(result.getToken());
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
			if(Utils.isNotEmpty(_edt.getText().toString())){
				_edt.setError(null);
			}else{
				_edt.setError(_msg);
			}
		}
		
	}
	public static  boolean contains(final String[] array, final String v) {
	    if (v != null) {
	        for (final String e : array)
	            if (e.contains(v))
	                return true;
	    } 

	    return false;
	}

}
