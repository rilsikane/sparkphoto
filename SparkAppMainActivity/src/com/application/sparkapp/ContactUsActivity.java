package com.application.sparkapp;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ContactUsActivity extends Activity {
	private Utils utils;
	private EditText subject,title;
	private int subSel;
	private AlertDialog subDialog;
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
									subject.setText("Subject: "+subject_items[0]);
									break;
								case 1:
									title.setVisibility(View.GONE);
									subSel=2;
									subject.setText("Subject: "+subject_items[1]);
									break;
								case 2:
									title.setVisibility(View.GONE);
									subSel=3;
									subject.setText("Subject: "+subject_items[2]);
									break;
								case 3:
									title.setVisibility(View.GONE);
									subSel=4;
									subject.setText("Subject: "+subject_items[3]);
									break;
								case 4:
									title.setVisibility(View.VISIBLE);
									subSel=5;
									subject.setText("Subject: "+subject_items[4]);
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
}
