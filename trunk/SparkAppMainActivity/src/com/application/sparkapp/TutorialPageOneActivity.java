package com.application.sparkapp;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

@SuppressLint("ValidFragment")
public class TutorialPageOneActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_tutorial_page_one);

		List<Fragment> fragments = getFragments();

		MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

		ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if(arg0==2){
					Intent i = new Intent(TutorialPageOneActivity.this,MainPhotoSelectActivity.class);
					startActivity(i);
					finish();
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				}
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		pager.setAdapter(pageAdapter);
	}
	@Override
	public void onBackPressed(){
		if(getIntent().hasExtra("INTENT_FROM")){
			if(getIntent().getStringExtra("INTENT_FROM").equals("touLogin")){
				Intent i = new Intent(TutorialPageOneActivity.this,MainPhotoSelectActivity.class);
				startActivity(i);
			}else if(getIntent().getStringExtra("INTENT_FROM").equals("facebookLogin")||getIntent().getStringExtra("INTENT_FROM").equals("emailLogin")){
				Intent i = new Intent(TutorialPageOneActivity.this,MainPhotoSelectActivity.class);
				startActivity(i);
			}					
			finish();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		}else{
			Intent i = new Intent(TutorialPageOneActivity.this,MainPhotoSelectActivity.class);
			startActivity(i);
			finish();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		}
	}

	private List<Fragment> getFragments() {

		List<Fragment> fList = new ArrayList<Fragment>();

		fList.add(MyFragment.newInstance("1"));

		fList.add(MyFragment.newInstance("2"));

		fList.add(MyFragment.newInstance("3"));

		return fList;

	}

	public class MyPageAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragments;

		public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {

			super(fm);

			this.fragments = fragments;

		}

		@Override
		public Fragment getItem(int position) {

			return this.fragments.get(position);

		}

		@Override
		public int getCount() {

			return this.fragments.size();

		}

	}

	@SuppressLint("NewApi") public static class MyFragment extends Fragment {

		public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

		public final static MyFragment newInstance(String message)

		{

			MyFragment f = new MyFragment();

			Bundle bdl = new Bundle(1);

			bdl.putString(EXTRA_MESSAGE, message);

			f.setArguments(bdl);

			return f;

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,

		Bundle savedInstanceState) {

			String message = getArguments().getString(EXTRA_MESSAGE);

			View v = inflater.inflate(R.layout.activity_tutorial_page_teo, container,false);
			RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.sparkTo);
			if(message.equals("1")){
				rl.setBackground(getResources().getDrawable(R.drawable.spark_guidemain1));
			}
			if(message.equals("2")){
				rl.setBackground(getResources().getDrawable(R.drawable.spark_guidemain2));
			}
			if(message.equals("3")){
				rl.setBackground(getResources().getDrawable(R.drawable.signup_background));
			}
			
			return v;

		}

	}

}
