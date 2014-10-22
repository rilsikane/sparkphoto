package com.application.sparkapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShippingAddressActivity extends Activity {
	private Utils utils;
	private ImageView goToPreviousPage;
	private TextView confirmBtn;
	private ProgressWheel pw_two;
	boolean running;
	int progress = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_shipping_address);
		System.gc();
		utils = new Utils(this, this);
		RelativeLayout fullGuid = (RelativeLayout) findViewById(R.id.imageGuid);
		
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.address_background, utils.getScreenWidth(), utils.getScreenHeight()));
		fullGuid.setBackgroundDrawable(ob);
		goToPreviousPage = (ImageView) findViewById(R.id.imageView1);
		confirmBtn = (TextView) findViewById(R.id.textView2);						
        
		confirmBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(ShippingAddressActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.custom_loading_dialog);
				RelativeLayout closeDialog =  (RelativeLayout) dialog.findViewById(R.id.close_dialog_layout);
				pw_two = (ProgressWheel) dialog.findViewById(R.id.progressBarTwo);
				closeDialog.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				
				final Runnable r = new Runnable() {
					public void run() {
						running = true;
						while(progress<361) {
							pw_two.incrementProgress();
							pw_two.setText((progress*100)/360+"%");
							progress++;
							try {
								Thread.sleep(15);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						running = false;
						dialog.dismiss();
						Intent i = new Intent(ShippingAddressActivity.this,MainPhotoSelectActivity.class);
						startActivity(i);
						overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						finish();
					}
		        };
				
		        if(!running) {
					progress = 0;
					pw_two.resetCount();
					Thread s = new Thread(r);
					s.start();
				}
		        
				dialog.show();
			}
		});
		goToPreviousPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ShippingAddressActivity.this,ShippingPageActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
	}


}
