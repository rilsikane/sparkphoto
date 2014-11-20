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
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;

@SuppressLint("NewApi")
public class ProfilePageActivity extends Activity {
	private Utils utils;
	private ImageView backIcon;
	private TextView comfirmText;
	private EditText changePass;
	private int occSel,servSel;
	private Calendar myCalendar;
	private AlertDialog levelDialog, occuDialog, serDialog;
	private EditText email, firstname, lastname, nric, password,phoneno, service, occuption, dob, gender;
	private static CharSequence[] service_items = { "m1", "Singtel", "Starhub",
	"MyRepublic" };
	private static CharSequence[] occ_items = { "Administrative & Secretarial",
			"Consultant ", "Events management", "Home-maker",
			"Human Resources", "National Service","Pre-university Student", 
			"Professional","Public Relations","Self-employed", 
			"University Post-graduate","University Undergraduate", "Others" };
	private UserDto userDto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.activity_profile_page);
		System.gc();
		backIcon = (ImageView) findViewById(R.id.imageView1);
		utils = new Utils(this, this);
		utils = new Utils(getApplicationContext(), this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.setting_page, utils.getScreenWidth(), utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		firstname = (EditText) findViewById(R.id.editText1);
		lastname = (EditText) findViewById(R.id.editText2);
		nric = (EditText) findViewById(R.id.editText3);
		email = (EditText) findViewById(R.id.editText4);
		phoneno = (EditText) findViewById(R.id.editText9);
		service = (EditText) findViewById(R.id.editText10);
		occuption = (EditText) findViewById(R.id.editText11);
		gender = (EditText) findViewById(R.id.editText8);
		dob = (EditText) findViewById(R.id.editText7);
		
		firstname.addTextChangedListener(new EditTextWatcher(firstname, "Please enter first name"));
		lastname.addTextChangedListener(new EditTextWatcher(lastname, "Please enter last name"));
		nric.addTextChangedListener(new EditTextWatcher(nric, "Please enter NRIC/FIN"));
		phoneno.addTextChangedListener(new EditTextWatcher(phoneno, "Please enter Phone Number"));
		service.addTextChangedListener(new EditTextWatcher(service, "Please select Service"));
		occuption.addTextChangedListener(new EditTextWatcher(occuption, "Please select Occupation"));
		gender.addTextChangedListener(new EditTextWatcher(gender, "Please select Gender"));
		dob.addTextChangedListener(new EditTextWatcher(dob, "Please select Date of Birth"));
		dob.setInputType(0);
		new InitAndLoadData().execute();
		
		myCalendar = Calendar.getInstance();
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
		dob.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(ProfilePageActivity.this, date,
						myCalendar.get(Calendar.YEAR), myCalendar
								.get(Calendar.MONTH), myCalendar
								.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		service.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

				AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePageActivity.this);
				builder.setTitle("Select Services");
				builder.setSingleChoiceItems(service_items, -1,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								switch (item) {
								case 0:
									servSel=1;
									service.setText(service_items[0]);
									break;
								case 1:
									servSel=2;
									service.setText(service_items[1]);
									break;
								case 2:
									servSel=3;
									service.setText(service_items[2]);
									break;
								case 3:
									servSel=4;
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
				

				AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePageActivity.this);
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

				final CharSequence[] items = { "Male", "Female" };

				AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePageActivity.this);
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
		
		comfirmText = (TextView) findViewById(R.id.textView2);
		changePass = (EditText) findViewById(R.id.editText5);
		
		changePass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!Utils.isNotEmpty(userDto.getFb_access_token())){
				Intent i = new Intent(ProfilePageActivity.this, ChangePassActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
				}
			}
		});
		comfirmText.setOnClickListener(new OnClickListener() {
			
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
						&& utils.isNotEmpty(firstname.getText().toString())) {
					

						userDto.setFirstname(firstname.getText().toString());
						userDto.setLastname(lastname.getText().toString());
						userDto.setNric_fin(nric.getText().toString());
						userDto.setEmail(email.getText().toString());
						userDto.setPhone(phoneno.getText().toString());
						userDto.setPhone_service(servSel+"");
						userDto.setOccupation(occSel+"");
						userDto.setBirthday(dob.getText().toString());
						userDto.setGender("Female".equals(gender.getText().toString()) ? "2" : "1");
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
				if (email.getText().toString().isEmpty()) {
					email.setError("Please enter Email");
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
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent i = new Intent(ProfilePageActivity.this, SettingPageActivity.class);				 
                 startActivity(i);
                 overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                 finish();
			}
		});
	}
	@Override
	public void onBackPressed(){
		 Intent i = new Intent(ProfilePageActivity.this, SettingPageActivity.class);				 
         startActivity(i);
         overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
         finish();
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
    
	public class InitAndLoadData extends AsyncTask<String, Void, UserDto>
			implements OnCancelListener {
		ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(ProfilePageActivity.this,
					"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected UserDto doInBackground(String... params) {
			UserVO user = Entity.query(UserVO.class).where("id").eq(1).execute();
			UserDto common = JSONParserForGetList.getInstance().getUserStatus(user.ac_token);
			common.setAccess_token(user.ac_token);
			return common;
		}

		@Override
		protected void onPostExecute(UserDto result) {
			super.onPostExecute(result);
			if(result!=null){
				firstname.setText(result.getFirstname());
				lastname.setText(result.getLastname());
				nric.setText(result.getNric_fin());
				email.setText(result.getEmail());
				changePass.setText(result.getPassword());
				phoneno.setText(result.getPhone());
				service.setText(Utils.isNotEmpty(result.getPhone_service()) ? service_items[ 
						Integer.parseInt(result.getPhone_service())-1]:"");
				occuption.setText(Utils.isNotEmpty(result.getOccupation())? 
						occ_items[Integer.parseInt(result.getOccupation())]:"");
				occSel = Utils.isNotEmpty(result.getOccupation())?Integer.parseInt(result.getOccupation()):0;
				servSel =  Utils.isNotEmpty(result.getPhone_service())?Integer.parseInt(result.getPhone_service()):0;
				dob.setText(result.getBirthday());
				gender.setText("1".equals(result.getGender()) ? "Male" : "Female");
				userDto=result;
				mProgressHUD.dismiss();
			}
			}

		@Override
		public void onCancel(DialogInterface arg0) {
			mProgressHUD.dismiss();
		}


	}

	public class EditProfileData extends AsyncTask<String, Void, CommonDto>
			implements OnCancelListener {
		ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(ProfilePageActivity.this,
					"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected CommonDto doInBackground(String... params) {
			// TODO Auto-generated method stub
			CommonDto common = JSONParserForGetList.getInstance().EditProfile(
					userDto,"debug");
			return common;
		}

		@Override
		protected void onPostExecute(CommonDto result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.isFlag()) {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							ProfilePageActivity.this);
					builder1.setMessage("Profile Edited");
					builder1.setCancelable(true);
					builder1.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									// By pass to term of use main activity
									Intent i = new Intent(
											ProfilePageActivity.this,
											SettingPageActivity.class);
									startActivity(i);
									finish();
									overridePendingTransition(
											R.anim.slide_in_left,
											R.anim.slide_out_left);
								}
							});
					AlertDialog alert11 = builder1.create();
					alert11.show();

				} else {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							ProfilePageActivity.this);

					String[] msgs = result.getMsg().replaceAll("\\[", "")
							.replaceAll("\\]", "").split(",");
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
}
