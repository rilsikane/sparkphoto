package com.application.sparkapp;

import java.util.Calendar;

import com.application.sparkapp.util.DateUtil;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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

public class YourDetailActivity extends Activity{

	public Utils utils;
	public ImageView backIcon,nricInfoIcon;
	private PopupWindow pwMyPopWindow;
	private EditText dob;
	private Calendar myCalendar;
	private TextView goToNextPage;
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
		goToNextPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(YourDetailActivity.this,ShippingAddressActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
		
		myCalendar = Calendar.getInstance();
		dob = (EditText) findViewById(R.id.editText7);
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
}
