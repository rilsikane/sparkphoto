package com.application.sparkapp;

import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShippingPageActivity extends Activity {
	private Utils utils;
	private ImageView goToPreviousPage;
	private TextView goToNextPage;
	private EditText email,firstname,lastname,nric,password,cfPassword,phoneno,service,occuption,dob,gender;
	private AlertDialog levelDialog, occuDialog,serDialog;
	private static CharSequence[] service_items = { "m1", "Singtel", "Starhub",
	"MyRepublic" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_shipping_page);
		System.gc();
		
		utils = new Utils(this, this);
		utils.setupUI(findViewById(R.id.imageGuid));
		RelativeLayout fullGuid = (RelativeLayout) findViewById(R.id.imageGuid);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.address_background, utils.getScreenWidth(), utils.getScreenHeight()));
		fullGuid.setBackgroundDrawable(ob);
		
		firstname = (EditText) findViewById(R.id.editText1);
		lastname = (EditText) findViewById(R.id.editText2);
		nric = (EditText) findViewById(R.id.editText3);
		email = (EditText) findViewById(R.id.editText4);
		phoneno = (EditText) findViewById(R.id.editText9);
		service = (EditText) findViewById(R.id.editText10);
		gender = (EditText) findViewById(R.id.editText8);
		dob = (EditText) findViewById(R.id.editText7);
		
		goToPreviousPage = (ImageView) findViewById(R.id.imageView1);
		goToNextPage = (TextView) findViewById(R.id.textView2);
		
		
		service.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

				AlertDialog.Builder builder = new AlertDialog.Builder(ShippingPageActivity.this);
				builder.setTitle("Select Services");
				builder.setSingleChoiceItems(service_items, -1,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								switch (item) {
								case 0:
									service.setText(service_items[0]);
									break;
								case 1:
									service.setText(service_items[1]);
									break;
								case 2:
									service.setText(service_items[2]);
									break;
								case 3:
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
		gender.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final CharSequence[] items = { " Male ", " Female " };

				AlertDialog.Builder builder = new AlertDialog.Builder(
						ShippingPageActivity.this);
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
		goToNextPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ShippingPageActivity.this,ShippingAddressActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();
			}
		});
		goToPreviousPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ShippingPageActivity.this,ImagePageSummaryActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
		UserVO user = Entity.query(UserVO.class).execute();
		if(user!=null){
			int selService = user.phone_service!=null ? Integer.parseInt(user.phone_service) : 0;
			firstname.setText(user.firstname);
			lastname.setText(user.lastname);
			nric.setText(user.nric_fin);
			email.setText(user.email);
			phoneno.setText(user.phone);
			service.setText(service_items[selService]);
			dob.setText(user.birthday);
			gender.setText("0".equals(user.gender)?"Male":"Female");
		}
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(ShippingPageActivity.this,ImagePageSummaryActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}
	@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}
