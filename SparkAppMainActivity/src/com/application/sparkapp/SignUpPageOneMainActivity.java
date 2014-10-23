package com.application.sparkapp;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sparkapp.dto.UserDto;

@SuppressLint("SimpleDateFormat") public class SignUpPageOneMainActivity extends Activity {

	private Utils utils;
	private EditText email,firstname,lastname,nric,password,cfPassword,phoneno,service,occuption,dob;
	private UserDto userDto;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_sign_up_page_one_main);
		System.gc();
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
		final EditText infoIconForNRIC = (EditText) findViewById(R.id.editText3);
		TextView goToNextPage = (TextView) findViewById(R.id.textView2);
		dob = (EditText) findViewById(R.id.editText7);
		dob.setInputType(0);
		utils = new Utils(getApplicationContext(), this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.signup_background, utils.getScreenWidth(), utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		
		firstname = (EditText) findViewById(R.id.editText1);
		lastname = (EditText) findViewById(R.id.editText2);
		nric = (EditText) findViewById(R.id.editText3);
		email = (EditText) findViewById(R.id.editText4);
		password = (EditText) findViewById(R.id.editText5);
		cfPassword = (EditText) findViewById(R.id.editText6);
		phoneno = (EditText) findViewById(R.id.editText9);
		service = (EditText) findViewById(R.id.editText10);
		occuption = (EditText) findViewById(R.id.editText11);
		if(getIntent().hasExtra("userDto")){
			userDto = (UserDto) getIntent().getExtras().get("userDto");
		}
		if(userDto!=null){
			firstname.setText(userDto.getFirstname());
			lastname.setText(userDto.getLastname());
			nric.setText(userDto.getNric_fin());
			email.setText(userDto.getEmail());
			password.setText(userDto.getPassword());
			cfPassword.setText(userDto.getPassword());
			phoneno.setText(userDto.getPhone());
			service.setText(userDto.getPhone_service());
			occuption.setText(userDto.getOccupation());
		}else{
			userDto = new UserDto();
		}
		
		
		final Calendar myCalendar = Calendar.getInstance();

		final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		    @Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear,
		            int dayOfMonth) {
		        // TODO Auto-generated method stub
		        myCalendar.set(Calendar.YEAR, year);
		        myCalendar.set(Calendar.MONTH, monthOfYear);
		        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		        

		        dob.setText(year+"-"+((monthOfYear+1)<10 ? "0":"")+(monthOfYear+1) +"-"+dayOfMonth);
		    }

		};
		
		dob.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(SignUpPageOneMainActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
	                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		
		goToNextPage.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi") @Override
			public void onClick(View v) {
				
					if(utils.isNotEmpty(password.getText().toString())&& utils.isNotEmpty(cfPassword.getText().toString())){
						if(!cfPassword.getText().toString().equals(password.getText().toString())){
							cfPassword.setError("Password is not match");
						}else{
							userDto.setFirstname(firstname.getText().toString());
							userDto.setLastname(lastname.getText().toString());
							userDto.setNric_fin(nric.getText().toString());
							userDto.setPassword(password.getText().toString());
							userDto.setEmail(email.getText().toString());
							userDto.setPhone(phoneno.getText().toString());
							userDto.setPhone_service(service.getText().toString());
							userDto.setOccupation(occuption.getText().toString());
							userDto.setBirthday(dob.getText().toString());
							
							Intent i = new Intent(SignUpPageOneMainActivity.this,AddressMainActivity.class);
							i.putExtra("userDto",(Parcelable) userDto);
							startActivity(i);
							overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
							finish();
						}
					}else if(password.getText().toString().isEmpty()){
						password.setError("Please enter password");
					}else if(cfPassword.getText().toString().isEmpty()){
						cfPassword.setError("Please enter confirm password");
					}					
			}
		});
		infoIconForNRIC.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int DRAWABLE_LEFT = 0;
	            final int DRAWABLE_TOP = 1;
	            final int DRAWABLE_RIGHT = 2;
	            final int DRAWABLE_BOTTOM = 3;
	            if(event.getAction() == MotionEvent.ACTION_UP) {
	                if(event.getRawX() >= (infoIconForNRIC.getRight() - infoIconForNRIC.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
	                    // your action here
//	                	Toast.makeText(getApplicationContext(), "Hello Information", Toast.LENGTH_SHORT).show();
	                 return true;
	                }
	            }
	            return false;
			}
		});
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SignUpPageOneMainActivity.this,SparkAppMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
		
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(SignUpPageOneMainActivity.this,SparkAppMainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
		
	}
	

}
