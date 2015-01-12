package com.application.sparkapp;

import com.application.sparkapp.SignUpPageOneMainActivity.EditTextWatcher;
import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ContactUsActivity extends Activity {
	private Utils utils;
	private EditText subject,title,name,phone,email,msg;
	private int subSel;
	private AlertDialog subDialog;
	private TextView btnSend;
	private static CharSequence[] subject_items = { "General enquiries",
		"Order enquiries ", "Sponsorship enquiries", "Feedback", "Other" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_contact_us);
		System.gc();
		utils = new Utils(this, this);
		utils = new Utils(getApplicationContext(), this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.setting_page, utils.getScreenWidth(), utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		title = (EditText) findViewById(R.id.editText10);
		subject = (EditText) findViewById(R.id.editText8);
		btnSend = (TextView) findViewById(R.id.textView2);
		name = (EditText) findViewById(R.id.editText3);
		phone = (EditText) findViewById(R.id.editText4);
		email = (EditText) findViewById(R.id.editText7);
		msg = (EditText) findViewById(R.id.editText9);
		
		title.addTextChangedListener(new EditTextWatcher(title, "Please enter title"));
		subject.addTextChangedListener(new EditTextWatcher(subject, "Please enter subject"));
		name.addTextChangedListener(new EditTextWatcher(name, "Please enter name"));
		email.addTextChangedListener(new EditTextWatcher(email, "Please enter phone"));
		phone.addTextChangedListener(new EditTextWatcher(phone, "Please enter password"));
		msg.addTextChangedListener(new EditTextWatcher(msg, "Please enter confirm message"));
		
		subject.setInputType(0);
		subject.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub			
				AlertDialog.Builder builder = new AlertDialog.Builder(ContactUsActivity.this);
				builder.setTitle("Select Subject");
				builder.setSingleChoiceItems(subject_items, -1,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								switch (item) {
								case 0:
									title.setVisibility(View.GONE);
									subSel=1;
									subject.setText(subject_items[0]);
									break;
								case 1:
									title.setVisibility(View.GONE);
									subSel=2;
									subject.setText(subject_items[1]);
									break;
								case 2:
									title.setVisibility(View.GONE);
									subSel=3;
									subject.setText(subject_items[2]);
									break;
								case 3:
									title.setVisibility(View.GONE);
									subSel=4;
									subject.setText(subject_items[3]);
									break;
								case 4:
									title.setVisibility(View.VISIBLE);
									subSel=5;
									subject.setText(subject_items[4]);
								}							
								subDialog.dismiss();
							}
						});
				subDialog = builder.create();
				subDialog.show();
			}
		});
		ImageView goBack = (ImageView) findViewById(R.id.imageView1);
		goBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ContactUsActivity.this, SettingPageActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
		});
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if ( utils.isNotEmpty(name.getText().toString())
						&& utils.isNotEmpty(email.getText().toString())
						&& utils.isNotEmpty(phone.getText().toString())
						&& utils.isNotEmpty(email.getText().toString())
						&& utils.isNotEmpty(subject.getText().toString())
						&& utils.isNotEmpty(msg.getText().toString())) {
					UserVO user = Entity.query(UserVO.class).where("id").eq("1").execute();
					if(user!=null){
						
						String subjectTxt = "";
						if(subSel==5){
							subjectTxt = title.getText().toString();
						}else{
							subjectTxt = subject.getText().toString();
						}
						
						UserDto userDto = JSONParserForGetList.getInstance().getUserStatus(user.ac_token);
						new InitAndLoadData(name.getText().toString(), phone.getText().toString(), 
								email.getText().toString(), subjectTxt, msg.getText().toString(), 
								userDto.getAccess_token()).execute();
					}
				}else{
					if (email.getText().toString().isEmpty()) {
						email.setError("Please enter Email");
					} 
					if (name.getText().toString().isEmpty()) {
						name.setError("Please enter name");
					}
					if (phone.getText().toString().isEmpty()) {
						phone.setError("Please enter phone");
					}
					if (subject.getText().toString().isEmpty()) {
						subject.setError("Please enter subject");
					}
					if (msg.getText().toString().isEmpty()) {
						msg.setError("Please enter subject");
					}
				}
				
			}
		});
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(ContactUsActivity.this, SettingPageActivity.class);				 
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
	public class InitAndLoadData extends AsyncTask<String, Void, CommonDto> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		String email,name,phone,subject,msg,ac;
		
		public InitAndLoadData(String name,String phone,String email,String subject,String msg,String ac){
			this.name = name;
			this.phone = phone;
			this.email = email;
			this.subject = subject;
			this.msg = msg;
			this.ac = ac;
		}
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(ContactUsActivity.this,"Loading ...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected CommonDto doInBackground(String... params) {
			// TODO Auto-generated method stub			
			
			return JSONParserForGetList.getInstance().contactUs(name, phone, email, subject, msg, ac);
		}
		
		@Override
		protected void onPostExecute(CommonDto result) {
			super.onPostExecute(result);
			if (result != null) {
				if(result.isFlag()){
					AlertDialog.Builder builder1 = new AlertDialog.Builder(ContactUsActivity.this);
		            builder1.setCancelable(true);
		            builder1.setMessage("Success!");
		            builder1.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		                    Intent i = new Intent(ContactUsActivity.this, SettingPageActivity.class);				 
			                startActivity(i);
			                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			                finish();
		                }
		            });
		            AlertDialog alert11 = builder1.create();
		            alert11.show();
					mProgressHUD.dismiss();
				}else{
					 AlertDialog.Builder builder1 = new AlertDialog.Builder(ContactUsActivity.this);
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
