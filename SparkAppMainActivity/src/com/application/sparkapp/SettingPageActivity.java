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

import com.application.sparkapp.model.TempImage;
import com.application.sparkapp.model.UserVO;
import com.facebook.Session;
import com.roscopeco.ormdroid.Entity;

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
		UserVO user = Entity.query(UserVO.class).where("id").eq("1").execute();
		TextView credit = (TextView) findViewById(R.id.textView);
		if(user!=null){
		credit.setText(user.numberPictureCanUpload + "  FREE PHOTO CREDITS");
		}else{
		credit.setText("0  FREE PHOTO CREDITS");	
		}
		
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
			    .setMessage("Are you sure you would like to log out?")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	if(Session.getActiveSession()!=null){
							Session.getActiveSession().closeAndClearTokenInformation();
						}
			        	 UserVO user = Entity.query(UserVO.class).where("id").eq(1).execute();
			        	if(user!=null){
			        		List<TempImage> tempList = Entity.query(TempImage.class).executeMulti();
				             if(tempList!=null){
				             	for(TempImage temp : tempList){
				             		temp.delete();
				             	}
				             }
				             user.status = "D";
				             user.save();
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
				tempMenuList.add("Address");
//				tempMenuList.add("Link with Facebook");
//				tempMenuList.add("Link with Dropbox");
				tempMenuList.add("Terms of use");
				tempMenuList.add("About us");
				tempMenuList.add("FAQ");
				//tempMenuList.add("Contact us");		
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
			if(position==0||position==3||position==4||position==5||position==1||position==2){
				convertView = inflater.inflate(R.layout.each_setting_one, null);
				ImageView logoIc = (ImageView) convertView.findViewById(R.id.imageView1);
				TextView menuName = (TextView) convertView.findViewById(R.id.textView1);
				RelativeLayout settingClick = (RelativeLayout) convertView.findViewById(R.id.settingClick);
				settingClick.setOnClickListener(new ManageSettingClick(position));
				menuName.setText(tempMenuList.get(position));
				if(position==0){
					logoIc.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
				}
				if(position==1){
					logoIc.setImageDrawable(getResources().getDrawable(R.drawable.street_icon));
				}
				if(position==2){
					logoIc.setImageDrawable(getResources().getDrawable(R.drawable.term_ic));
				}
				if(position==3){
					logoIc.setImageDrawable(getResources().getDrawable(R.drawable.aboutus_ic));
				}
				if(position==4){
					logoIc.setImageDrawable(getResources().getDrawable(R.drawable.faq_ic));
				}
				if(position==5){
					logoIc.setImageDrawable(getResources().getDrawable(R.drawable.contactus_ic));
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
			//Link to profile page
			if(position==0){
				Intent i = new Intent(SettingPageActivity.this, ProfilePageActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
			//Link to address page
			if(position==1){
				Intent i = new Intent(SettingPageActivity.this, AddressSettingPageActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
			//Link to term of use page
			if(position==2){
				Intent i = new Intent(SettingPageActivity.this, TermOfUseSettingMainActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
			//Link to about page
			if(position==3){
				Intent i = new Intent(SettingPageActivity.this, AboutUsActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
			//Link to faq page
			if(position==4){
				Intent i = new Intent(SettingPageActivity.this, FaqActivity.class);				 
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
			//Link to contact us page
			if(position==5){
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
