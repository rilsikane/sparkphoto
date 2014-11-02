package com.application.sparkapp;

import java.util.Arrays;
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
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.application.sparkapp.AddressMainActivity.InitAndLoadData;
import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;

@SuppressLint("SimpleDateFormat")
public class SignUpPageOneMainActivity extends Activity {

	private Utils utils;
	private EditText email, firstname, lastname, nric, password, cfPassword,
			phoneno, service, occuption, dob, gender;
	private UserDto userDto;
	private AlertDialog levelDialog, occuDialog, serDialog;
	private int occSel,servSel;
	private boolean isValid;
	private static CharSequence[] service_items = { "m1", "SingT", "StarH",
	"MyRepublic" };
	private static CharSequence[] occ_items = { "Administrative & Secretarial",
			"Consultant ", "Events management", "Home-maker",
			"Human Resources", "National Service","Pre-university Student", 
			"Professional","Public Relations","Self-employed", 
			"University Post-graduate","University Undergraduate", "Others" };
	
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
			service.setText(Utils.isNotEmpty(userDto.getPhone_service()) ? service_items[ 
					Integer.parseInt(userDto.getPhone_service())]:"");
			occuption.setText(Utils.isNotEmpty(userDto.getOccupation())? 
					occ_items[Integer.parseInt(userDto.getOccupation())]:"");
			occSel = Utils.isNotEmpty(userDto.getOccupation())?Integer.parseInt(userDto.getOccupation()):0;
			servSel =  Utils.isNotEmpty(userDto.getPhone_service())?Integer.parseInt(userDto.getPhone_service()):0;
			dob.setText(userDto.getBirthday());
			gender.setText("0".equals(userDto.getGender()) ? "Male" : "Female");
		} else {
			userDto = new UserDto();
		}

		firstname.addTextChangedListener(new EditTextWatcher(firstname, "Please enter first name"));
		lastname.addTextChangedListener(new EditTextWatcher(lastname, "Please enter last name"));
		nric.addTextChangedListener(new EditTextWatcher(nric, "Please enter NRIC/FIN"));
		email.addTextChangedListener(new EditTextWatcher(email, "Please enter Email"));
		password.addTextChangedListener(new EditTextWatcher(password, "Please enter password"));
		cfPassword.addTextChangedListener(new EditTextWatcher(cfPassword, "Please enter confirm password"));
		phoneno.addTextChangedListener(new EditTextWatcher(phoneno, "Please enter Phone Number"));
		service.addTextChangedListener(new EditTextWatcher(service, "Please select Service"));
		occuption.addTextChangedListener(new EditTextWatcher(occuption, "Please select Occupation"));
		gender.addTextChangedListener(new EditTextWatcher(gender, "Please select Gender"));
		dob.addTextChangedListener(new EditTextWatcher(dob, "Please select Date of Birth"));
		
		final Calendar myCalendar = Calendar.getInstance();

		final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				dob.setText(year + "-" + ((monthOfYear + 1) < 10 ? "0" : "")
						+ (monthOfYear + 1) + "-" + dayOfMonth);
			}

		};
		service.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

				AlertDialog.Builder builder = new AlertDialog.Builder(
						SignUpPageOneMainActivity.this);
				builder.setTitle("Select Services");
				builder.setSingleChoiceItems(service_items, -1,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								switch (item) {
								case 0:
									servSel=0;
									service.setText(service_items[0]);
									break;
								case 1:
									servSel=1;
									service.setText(service_items[1]);
									break;
								case 2:
									servSel=2;
									service.setText(service_items[2]);
									break;
								case 3:
									servSel=3;
									service.setText(service_items[3]);
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
				

				AlertDialog.Builder builder = new AlertDialog.Builder(
						SignUpPageOneMainActivity.this);
				builder.setTitle("Select Occupations");
				builder.setSingleChoiceItems(occ_items, -1,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								switch (item) {
								case 0:
									occSel=0;
									occuption.setText(occ_items[0]);
									break;
								case 1:
									occSel=1;
									occuption.setText(occ_items[1]);
									break;
								case 2:
									occSel=2;
									occuption.setText(occ_items[2]);
									break;
								case 3:
									occSel=3;
									occuption.setText(occ_items[3]);
									break;
								case 4:
									occSel=4;
									occuption.setText(occ_items[4]);
									break;
								case 5:
									occSel=5;
									occuption.setText(occ_items[5]);
									break;
								case 6:
									occSel=6;
									occuption.setText(occ_items[6]);
									break;
								case 7:
									occSel=7;
									occuption.setText(occ_items[7]);
									break;
								case 8:
									occSel=8;
									occuption.setText(occ_items[8]);
									break;
								case 9:
									occSel=9;
									occuption.setText(occ_items[9]);
									break;
								case 10:
									occSel=10;
									occuption.setText(occ_items[10]);
									break;
								case 11:
									occSel=11;
									occuption.setText(occ_items[11]);
									break;
								case 12:
									occSel=12;
									occuption.setText(occ_items[12]);
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

				if (utils.isNotEmpty(gender.getText().toString())
						&& utils.isNotEmpty(dob.getText().toString())
						&& utils.isNotEmpty(occuption.getText().toString())
						&& utils.isNotEmpty(service.getText().toString())
						&& utils.isNotEmpty(phoneno.getText().toString())
						&& utils.isNotEmpty(email.getText().toString())
						&& utils.isNotEmpty(nric.getText().toString())
						&& utils.isNotEmpty(lastname.getText().toString())
						&& utils.isNotEmpty(firstname.getText().toString())
						&& utils.isNotEmpty(password.getText().toString())
						&& utils.isNotEmpty(cfPassword.getText().toString())) {
					if (!cfPassword.getText().toString().equals(password.getText().toString())) {
						cfPassword.setError("Password is not match");
					} else {
						userDto.setFirstname(firstname.getText().toString());
						userDto.setLastname(lastname.getText().toString());
						userDto.setNric_fin(nric.getText().toString());
						userDto.setPassword(password.getText().toString());
						userDto.setEmail(email.getText().toString());
						userDto.setPhone(phoneno.getText().toString());
						userDto.setPhone_service(servSel+"");
						userDto.setOccupation(occSel+"");
						userDto.setBirthday(dob.getText().toString());
						userDto.setGender("Male".equals(gender.getText().toString()) ? "0" : "1");
						new InitAndLoadData().execute();
						
					}
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
				if (email.getText().toString().isEmpty()) {
					email.setError("Please enter Email");
				} 
				if (password.getText().toString().isEmpty()) {
					password.setError("Please enter password");
				}
				if (cfPassword.getText().toString().isEmpty()) {
					cfPassword.setError("Please enter confirm password");
				}
				if (dob.getText().toString().isEmpty()) {
					dob.setError("Please select Date of Birth");
				}
				if (gender.getText().toString().isEmpty()) {
					gender.setError("Please select Gender");
				}
				if (phoneno.getText().toString().isEmpty()) {
					phoneno.setError("Please enter Phone Number");
				}
				if (service.getText().toString().isEmpty()) {
					service.setError("Please select Service");
				}
				if (occuption.getText().toString().isEmpty()) {
					occuption.setError("Please select Occupation");
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
			if(utils.isNotEmpty(_edt.getText().toString())){
				_edt.setError(null);
			}else{
				_edt.setError(_msg);
			}
		}
		
	}

	public class InitAndLoadData extends AsyncTask<String, Void, CommonDto>
			implements OnCancelListener {
		ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(SignUpPageOneMainActivity.this,
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
			if (result != null && utils.isNotEmpty(result.getMsg())) {
			String[] msgs = result.getMsg().replaceAll("\\[", "")
					.replaceAll("\\]", "").split(",");
				if (msgs.length==4) {
					Intent i = new Intent(SignUpPageOneMainActivity.this,AddressMainActivity.class);
					i.putExtra("userDto", (Parcelable) userDto);
					startActivity(i);
					overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
					finish();
				} else {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							SignUpPageOneMainActivity.this);

					if (msgs != null && msgs.length > 0) {
						String msg = "Error Please try again "
								+ System.getProperty("line.separator");
						if (msgs != null && msgs.length > 0) {
							for (String ms : msgs) {
								msg += ("-" + ms + System
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
