package com.application.sparkapp;

import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProfilePageActivity extends Activity {
	private Utils utils;
	private ImageView backIcon;
	private TextView comfirmText;
	private EditText changePass;
	private int occSel,servSel;
	private AlertDialog levelDialog, occuDialog, serDialog;
	private EditText email, firstname, lastname, nric, password,phoneno, service, occuption, dob, gender;
	private static CharSequence[] service_items = { "m1", "Singtel", "Starhub",
	"MyRepublic" };
	private static CharSequence[] occ_items = { "Administrative & Secretarial",
			"Consultant ", "Events management", "Home-maker",
			"Human Resources", "National Service","Pre-university Student", 
			"Professional","Public Relations","Self-employed", 
			"University Post-graduate","University Undergraduate", "Others" };
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

				final CharSequence[] items = { " Male ", " Female " };

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
				// TODO Auto-generated method stub
				Intent i = new Intent(ProfilePageActivity.this, ChangePassActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
		});
		comfirmText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ProfilePageActivity.this, SettingPageActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
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
			return common;
		}

		@Override
		protected void onPostExecute(UserDto result) {
			super.onPostExecute(result);
			
				mProgressHUD.dismiss();
			}

		@Override
		public void onCancel(DialogInterface arg0) {
			mProgressHUD.dismiss();
		}


	}
}
