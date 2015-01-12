package com.application.sparkapp;

import java.util.Calendar;

import com.application.sparkapp.ProfilePageActivity.EditProfileData;
import com.application.sparkapp.ProfilePageActivity.InitAndLoadData;
import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.application.sparkapp.util.DateUtil;
import com.roscopeco.ormdroid.Entity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class YourDetailActivity extends Activity{

	public Utils utils;
	public ImageView backIcon,nricInfoIcon;
	private PopupWindow pwMyPopWindow;
	private EditText dob,firstname,lastname,nric;
	private Calendar myCalendar;
	private TextView goToNextPage;
	private UserDto userDto;
	private UserVO user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.yourdetail_layout);
		System.gc();
		utils = new Utils(getApplicationContext(), this);
		utils.setupUI(findViewById(R.id.root_id));
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(),R.drawable.signup_background, utils.getScreenWidth(),utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		
		goToNextPage = (TextView) findViewById(R.id.textView2);
		firstname =  (EditText) findViewById(R.id.editText1);
		lastname =  (EditText) findViewById(R.id.editText2);
		nric =  (EditText) findViewById(R.id.editText3);
		dob = (EditText) findViewById(R.id.editText7);
		
		
		new InitAndLoadData().execute();
		
		
		goToNextPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (utils.isNotEmpty(dob.getText().toString())
						&& utils.isNotEmpty(nric.getText().toString())
						&& utils.isNotEmpty(lastname.getText().toString())
						&& utils.isNotEmpty(firstname.getText().toString())) {
					
						
						userDto.setFirstname(firstname.getText().toString());
						userDto.setLastname(lastname.getText().toString());
						userDto.setNric_fin(nric.getText().toString());
						userDto.setBirthday(dob.getText().toString());
						
						if(!utils.isNotEmpty(userDto.getOccupation())){
							userDto.setOccupation("0");
						}
						if(!utils.isNotEmpty(userDto.getGender())){
							userDto.setGender("0");
						}
						new EditProfileData().execute();
						
					
					
				}
				if (firstname.getText().toString().isEmpty()) {
					firstname.setError("Please enter first name");
				}
				if (lastname.getText().toString().isEmpty()) {
					lastname.setError("Please enter last name");
				}
				if (nric.getText().toString().isEmpty()) {
					nric.setError("Please enter NRIC/FIN");
				}
				if (dob.getText().toString().isEmpty()) {
					dob.setError("Please select Date of Birth");
				}
			}
		});
		
		myCalendar = Calendar.getInstance();
		
		dob.setInputType(0);
		final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				dob.setText(DateUtil.toStringEngDateBySimpleFormat(myCalendar.getTime(), DateUtil.DEFAULT_DATE_PATTERN));
			}

		};
		dob.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(YourDetailActivity.this, date,
						myCalendar.get(Calendar.YEAR), myCalendar
								.get(Calendar.MONTH), myCalendar
								.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		
		nricInfoIcon = (ImageView) findViewById(R.id.imageView2);
		iniPopupWindowForNRIC();
		nricInfoIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (pwMyPopWindow.isShowing()) {

					pwMyPopWindow.dismiss();
				} else {

					pwMyPopWindow.showAsDropDown(nricInfoIcon);
				}
			}
		});
		backIcon = (ImageView) findViewById(R.id.imageView1);
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(YourDetailActivity.this,ImagePageSummaryActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
	}
	@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
	@Override
	public void onBackPressed(){		
		Intent i = new Intent(YourDetailActivity.this,ImagePageSummaryActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}
	private void iniPopupWindowForNRIC() {

		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.popup_for_phone, null);
		pwMyPopWindow = new PopupWindow(layout);
		pwMyPopWindow.setFocusable(true);
		TextView desc =(TextView) layout.findViewById(R.id.textView1);
		desc.setText("This is for us to verify you are a unique SPARK user and ensure each individual does not  create multiple accounts");
		pwMyPopWindow.setWidth(350);
		pwMyPopWindow.setHeight(250);
		
		pwMyPopWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bg_popupwindow_2));
		pwMyPopWindow.setOutsideTouchable(true);
	}

	public class EditProfileData extends AsyncTask<String, Void, CommonDto>
			implements OnCancelListener {
		ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(YourDetailActivity.this,
					"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected CommonDto doInBackground(String... params) {
			// TODO Auto-generated method stub
			CommonDto common = JSONParserForGetList.getInstance().EditProfile(
					userDto, "debug", false);
			return common;
		}

		@Override
		protected void onPostExecute(CommonDto result) {
			super.onPostExecute(result);
			if (result != null && utils.isNotEmpty(result.getMsg())) {
				String[] msgs = result.getMsg().replaceAll("\\[", "")
						.replaceAll("\\]", "").split("\\.");
				if (checkAddres(msgs) && !checkNRIC(msgs)) {
					
					Intent i = new Intent(YourDetailActivity.this,ShippingAddressActivity.class);
					i.putExtra("userDto", (Parcelable) userDto);
					startActivity(i);
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					finish();
				} else {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							YourDetailActivity.this);
					if (msgs != null && msgs.length > 0) {
						String msg = "Error: please try again. "
								+ System.getProperty("line.separator");
						if (msgs != null && msgs.length > 0) {
							for (String ms : msgs) {
								if (ms.contains("NRIC") ||ms.contains("above") ) {
								msg += ("-" + ms.replaceFirst(",", "") + System
										.getProperty("line.separator"));
								}
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
			}else if(result.isFlag()){
				Intent i = new Intent(YourDetailActivity.this,ShippingAddressActivity.class);
				i.putExtra("userDto", (Parcelable) userDto);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}else {
				mProgressHUD.dismiss();
			}

		}

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}

	}

	public class InitAndLoadData extends AsyncTask<String, Void, UserDto>
			implements OnCancelListener {
		ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(YourDetailActivity.this,
					"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected UserDto doInBackground(String... params) {
			user = Entity.query(UserVO.class).where("id").eq(1)
					.execute();
			UserDto common = JSONParserForGetList.getInstance().getUserStatus(
					user.ac_token);
			common.setAccess_token(user.ac_token);
			return common;
		}

		@Override
		protected void onPostExecute(UserDto result) {
			super.onPostExecute(result);
			if (result != null) {
				firstname.setText(result.getFirstname());
				lastname.setText(result.getLastname());
				nric.setText(result.getNric_fin());
				dob.setText(result.getBirthday());
				userDto = result;
				mProgressHUD.dismiss();
			}
		}

		@Override
		public void onCancel(DialogInterface arg0) {
			mProgressHUD.dismiss();
		}

}
	public boolean checkAddres(String[]msgs){
		boolean isContain = false;
		if(msgs!=null && msgs.length>0){
			for(int i=0;i<msgs.length;i++){
				if(msgs[i].contains("postal") || msgs[i].contains("street") || msgs[i].contains("block")||msgs[i].contains("otp")||msgs[i].contains("term")){
					isContain = true;
					break;
				}
			}
			
		}
		return isContain;
	}
	public boolean checkNRIC(String[]msgs){
		boolean isContain = false;
		if(msgs!=null && msgs.length>0){
			for(int i=0;i<msgs.length;i++){
				if(msgs[i].contains("NRIC") || msgs[i].contains("above")){
					isContain = true;
					break;
				}
			}
			
		}
		return isContain;
	}
}
