package com.application.sparkapp;

import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddressMainActivity extends Activity {

	private EditText address_block;
	private EditText address_street_name;
	private EditText address_unit_number;
	private EditText address_postal;
	private UserDto userDto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_address_main);
		System.gc();
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
		TextView goToNextPage = (TextView) findViewById(R.id.textView2);
		address_block = (EditText) findViewById(R.id.editText3);
		address_street_name = (EditText) findViewById(R.id.editText4);
		address_unit_number = (EditText) findViewById(R.id.editText7);
		address_postal = (EditText) findViewById(R.id.editText8);
		
		
		userDto = (UserDto) getIntent().getSerializableExtra("userDto");
		
		userDto.setAddress_block(address_block.getText().toString());
		userDto.setAddress_street_name(address_street_name.getText().toString());
		userDto.setAddress_unit_number(address_unit_number.getText().toString());
		userDto.setAddress_postal(address_postal.getText().toString());
		
		
		goToNextPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonDto common = JSONParserForGetList.getInstance().Register(userDto);
				if(common.isFlag()){
					final AlertDialog.Builder builder1 = new AlertDialog.Builder(AddressMainActivity.this);
		            builder1.setMessage("Register Completed");
		            builder1.setCancelable(true);
		            builder1.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		                    AlertDialog alert11 = builder1.create();
				            alert11.show();
							Intent intent = new Intent(AddressMainActivity.this,SparkAppMainActivity.class);
							startActivity(intent);
							overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
							finish();
		                }
		            });
		            
				}else{
					final AlertDialog.Builder builder1 = new AlertDialog.Builder(AddressMainActivity.this);
		            builder1.setMessage("Error  Please try again");
		            builder1.setCancelable(true);
		            builder1.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		                    AlertDialog alert11 = builder1.create();
				            alert11.show();
		                }
		            });
				}
			}
		});
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AddressMainActivity.this,SignUpPageOneMainActivity.class);
				intent.putExtra("userDto",(Parcelable) userDto);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed() {
		
	}

}
