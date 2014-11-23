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
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

@SuppressLint("ValidFragment")
public class TutorialPageOneActivity extends FragmentActivity {
	private ViewPager pager;
	private int x;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_tutorial_page_one);

		List<Fragment> fragments = getFragments();

		MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

		pager = (ViewPager) findViewById(R.id.viewpager);
		
		pager.setOnPageChangeListener(new PageListener());
		
		pager.setAdapter(pageAdapter);
	}
	public class PageListener extends SimpleOnPageChangeListener{
		private boolean isDrag=false;
		public void onPageSelected(int position) {
           if(position==1){
        	  pager.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						x = (int) event.getRawX();
						break;
					case MotionEvent.ACTION_UP:
						int now = (int) event.getRawX();
						if(!isDrag){
							Intent i = new Intent(TutorialPageOneActivity.this,MainPhotoSelectActivity.class);
							startActivity(i);
							finish();
							overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						}else{
							switch (pager.getCurrentItem()) {
							case 0:
								pager.setCurrentItem(1);
								break;
							case 1:
								if(now>x){
									pager.setCurrentItem(0);
								}else{
									Intent i = new Intent(TutorialPageOneActivity.this,MainPhotoSelectActivity.class);
									startActivity(i);
									finish();
									overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
								}
								break;		
							default:
								break;
							}
							
						}
						break;
					case MotionEvent.ACTION_MOVE:
						int nowX = (int) event.getRawX();
						int sumX = x - nowX;
						if(Math.abs(sumX)>5){
						isDrag = true;
						}else{
						isDrag = false;	
						}
					break;

					default:
						break;
					}
					return true;
				}
			});
           }
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			//isDrag = true;
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
//			if(arg0==0){
//				isDrag=false;
//			}else{
//				isDrag=true;
//			}
		}
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

//		fList.add(MyFragment.newInstance("3"));

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
