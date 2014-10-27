package com.application.sparkapp;

import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

@SuppressLint("SimpleDateFormat")
public class SignUpPageOneMainActivity extends Activity {

	private Utils utils;
	private EditText email, firstname, lastname, nric, password, cfPassword,
			phoneno, service, occuption, dob, gender;
	private UserDto userDto;
	private AlertDialog levelDialog, occuDialog,serDialog;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf",
				R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_sign_up_page_one_main);
		System.gc();
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
		final EditText infoIconForNRIC = (EditText) findViewById(R.id.editText3);
		TextView goToNextPage = (TextView) findViewById(R.id.textView2);
		dob = (EditText) findViewById(R.id.editText7);
		dob.setInputType(0);
		utils = new Utils(getApplicationContext(), this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
		BitmapDrawable ob = new BitmapDrawable(
				utils.decodeSampledBitmapFromResource(getResources(),
						R.drawable.signup_background, utils.getScreenWidth(),
						utils.getScreenHeight()));
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
		gender = (EditText) findViewById(R.id.editText8);

		if (getIntent().hasExtra("userDto")) {
			userDto = (UserDto) getIntent().getExtras().get("userDto");
		}
		if (userDto != null) {
			firstname.setText(userDto.getFirstname());
			lastname.setText(userDto.getLastname());
			nric.setText(userDto.getNric_fin());
			email.setText(userDto.getEmail());
			password.setText(userDto.getPassword());
			cfPassword.setText(userDto.getPassword());
			phoneno.setText(userDto.getPhone());
			service.setText(userDto.getPhone_service());
			occuption.setText(userDto.getOccupation());
			dob.setText(userDto.getBirthday());
			gender.setText("0".equals(userDto.getGender())?"Male":"Female");
		} else {
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

				dob.setText(year + "-" + ((monthOfYear + 1) < 10 ? "0" : "")+ (monthOfYear + 1) + "-" + dayOfMonth);
			}

		};
		service.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final CharSequence[] items = { "m1", "SingT","StarH","MyRepublic"};

				AlertDialog.Builder builder = new AlertDialog.Builder(SignUpPageOneMainActivity.this);
				builder.setTitle("Select Services");
				builder.setSingleChoiceItems(items, -1,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								switch (item) {
								case 0:
									service.setText(items[0]);
									break;
								case 1:
									// Your code when 2nd option seletced
									service.setText(items[1]);
									break;
								case 2:
									// Your code when 2nd option seletced
									service.setText(items[2]);
									break;
								case 3:
									// Your code when 2nd option seletced
									service.setText(items[3]);
									break;
								}
								serDialog.dismiss();
							}
						});
				serDialog = builder.create();
				serDialog.show();
			}
		});
		occuption.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final CharSequence[] items = { "Administrative & Secretarial", "Consultant "
						, "Events management","Home-maker"
						,"Human Resources","National Service"
						,"Others","Pre-university Student"
						,"Professional","Public Relations"
						,"University Post-graduate","University Undergraduate"
						,"Self-employed"};

				AlertDialog.Builder builder = new AlertDialog.Builder(SignUpPageOneMainActivity.this);
				builder.setTitle("Select Occupations");
				builder.setSingleChoiceItems(items, -1,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								switch (item) {
								case 0:
									occuption.setText(items[0]);
									break;
								case 1:
									occuption.setText(items[1]);
									break;
								case 2:
									occuption.setText(items[2]);
									break;
								case 3:
									occuption.setText(items[3]);
									break;
								case 4:
									occuption.setText(items[4]);
									break;
								case 5:
									occuption.setText(items[5]);
									break;
								case 6:
									occuption.setText(items[6]);
									break;
								case 7:
									occuption.setText(items[7]);
									break;
								case 8:
									occuption.setText(items[8]);
									break;
								case 9:
									occuption.setText(items[9]);
									break;
								case 10:
									occuption.setText(items[10]);
									break;
								case 11:
									occuption.setText(items[11]);
									break;
								case 12:
									occuption.setText(items[12]);
									break;
								}
								
								occuDialog.dismiss();
							}
						});
				occuDialog = builder.create();
				occuDialog.show();
			}
		});
		gender.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				final CharSequence[] items = { " Male ", " Female " };

				AlertDialog.Builder builder = new AlertDialog.Builder(
						SignUpPageOneMainActivity.this);
				builder.setTitle("Select Genders");
				builder.setSingleChoiceItems(items, -1,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								switch (item) {
								case 0:
									gender.setText(items[0]);
									break;
								case 1:
									// Your code when 2nd option seletced
									gender.setText(items[1]);
									break;
								}
								levelDialog.dismiss();
							}
						});
				levelDialog = builder.create();
				levelDialog.show();
			}
		});
		dob.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(SignUpPageOneMainActivity.this, date,
						myCalendar.get(Calendar.YEAR), myCalendar
								.get(Calendar.MONTH), myCalendar
								.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		goToNextPage.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {

				if (utils.isNotEmpty(password.getText().toString())
						&& utils.isNotEmpty(cfPassword.getText().toString())) {
					if (!cfPassword.getText().toString()
							.equals(password.getText().toString())) {
						cfPassword.setError("Password is not match");
					} else {
						userDto.setFirstname(firstname.getText().toString());
						userDto.setLastname(lastname.getText().toString());
						userDto.setNric_fin(nric.getText().toString());
						userDto.setPassword(password.getText().toString());
						userDto.setEmail(email.getText().toString());
						userDto.setPhone(phoneno.getText().toString());
						userDto.setPhone_service(service.getText().toString());
						userDto.setOccupation(occuption.getText().toString());
						userDto.setBirthday(dob.getText().toString());
						userDto.setGender("Male".equals(gender.getText().toString())?"0":"1");

						Intent i = new Intent(SignUpPageOneMainActivity.this,
								AddressMainActivity.class);
						i.putExtra("userDto", (Parcelable) userDto);
						startActivity(i);
						overridePendingTransition(R.anim.slide_in_left,
								R.anim.slide_out_left);
						finish();
					}
				} else if (password.getText().toString().isEmpty()) {
					password.setError("Please enter password");
				} else if (cfPassword.getText().toString().isEmpty()) {
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
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (event.getRawX() >= (infoIconForNRIC.getRight() - infoIconForNRIC
							.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds()
							.width())) {
						// your action here
						// Toast.makeText(getApplicationContext(),
						// "Hello Information", Toast.LENGTH_SHORT).show();
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
				Intent intent = new Intent(SignUpPageOneMainActivity.this,
						SparkAppMainActivity.class);
				startActivity(intent);
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
	public void onBackPressed() {
		Intent intent = new Intent(SignUpPageOneMainActivity.this,
				SparkAppMainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();

	}

}
