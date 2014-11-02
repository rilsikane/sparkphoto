package com.application.sparkapp;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.Session;

public class SettingPageActivity extends Activity {
	private Utils utils;
	private ImageView backIcon,logoutBtn;
	private ListView menuSetting;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_setting_page);
		System.gc();
		utils = new Utils(this, this);
		utils = new Utils(getApplicationContext(), this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.setting_page, utils.getScreenWidth(), utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		
		backIcon = (ImageView) findViewById(R.id.imageView1);
		logoutBtn = (ImageView) findViewById(R.id.imageView4);
		menuSetting = (ListView) findViewById(R.id.settingList);
		MenuListAdapter menuAdapter = new MenuListAdapter();
		menuSetting.setAdapter(menuAdapter);
		logoutBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub								
				new AlertDialog.Builder(SettingPageActivity.this)
			    .setTitle("Logout Confirmation")
			    .setMessage("Are you sure to logout?")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	if(Session.getActiveSession()!=null){
							Session.getActiveSession().closeAndClearTokenInformation();
						}
						Intent i = new Intent(SettingPageActivity.this, SparkAppMainActivity.class);				 
		                startActivity(i);
		                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		                finish();
			        }
			     })
			    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     })
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
								
			}
		});
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent i = new Intent(SettingPageActivity.this, MainPhotoSelectActivity.class);				 
                 startActivity(i);
                 overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                 finish();
			}
		});
		
	}
	@Override
	public void onBackPressed(){
		 Intent i = new Intent(SettingPageActivity.this, MainPhotoSelectActivity.class);				 
         startActivity(i);
         overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
         finish();
	}
	public class MenuListAdapter extends BaseAdapter{
		List<String> tempMenuList = new ArrayList<String>();
		public MenuListAdapter(){
				tempMenuList.add("Profile");
				tempMenuList.add("Link with Facebook");
				tempMenuList.add("Link with Dropbox");
				tempMenuList.add("Terms of use");
				tempMenuList.add("About us");
				tempMenuList.add("FAQ");
				tempMenuList.add("Contact us");		
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tempMenuList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return tempMenuList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if(position==0||position==3||position==4||position==5||position==6){
				convertView = inflater.inflate(R.layout.each_setting_one, null);
				ImageView logoIc = (ImageView) convertView.findViewById(R.id.imageView1);
				TextView menuName = (TextView) convertView.findViewById(R.id.textView1);
				RelativeLayout settingClick = (RelativeLayout) convertView.findViewById(R.id.settingClick);
				settingClick.setOnClickListener(new ManageSettingClick(position));
				menuName.setText(tempMenuList.get(position));
				if(position==0){
					logoIc.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
				}
				if(position==3){
					logoIc.setImageDrawable(getResources().getDrawable(R.drawable.term_ic));
				}
				if(position==4){
					logoIc.setImageDrawable(getResources().getDrawable(R.drawable.aboutus_ic));
				}
				if(position==5){
					logoIc.setImageDrawable(getResources().getDrawable(R.drawable.faq_ic));
				}
				if(position==6){
					logoIc.setImageDrawable(getResources().getDrawable(R.drawable.contactus_ic));
				}
				
			}else{
				convertView = inflater.inflate(R.layout.each_setting_two, null);
				TextView menuName = (TextView) convertView.findViewById(R.id.textView1);
				ImageView logoIc = (ImageView) convertView.findViewById(R.id.imageView1);
				ImageView check = (ImageView) convertView.findViewById(R.id.imageView2);
				menuName.setText(tempMenuList.get(position));
				if(position==1){
					logoIc.setImageDrawable(getResources().getDrawable(R.drawable.facebookic));
					if(Session.getActiveSession()==null){
						check.setImageDrawable(getResources().getDrawable(R.drawable.check_default));
					}else{
						check.setImageDrawable(getResources().getDrawable(R.drawable.check_selected));
					}
				}
				if(position==2){
					logoIc.setImageDrawable(getResources().getDrawable(R.drawable.dropboxic));
				}
			}
			return convertView;
		}
		
	}
	public class ManageSettingClick implements OnClickListener{
		private int position;
		public ManageSettingClick(int position){
			this.position = position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(position==4){
				Intent i = new Intent(SettingPageActivity.this, AboutUsActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
			if(position==5){
				Intent i = new Intent(SettingPageActivity.this, FaqActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
			if(position==6){
				Intent i = new Intent(SettingPageActivity.this, ContactUsActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
		}
		
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}
