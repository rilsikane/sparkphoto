package com.application.sparkapp;

import java.util.ArrayList;
import java.util.List;

import com.application.sparkapp.model.Login;
import com.application.sparkapp.model.TempImage;
import com.roscopeco.ormdroid.Entity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImagePageSummaryActivity extends Activity {
	private Utils utils;
	private ListView summaryList;
	private TextView goToNextPage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image_page_summary);
		System.gc();
		utils = new Utils(this, this);
		RelativeLayout fullGuid = (RelativeLayout) findViewById(R.id.imageGuid);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.address_background, utils.getScreenWidth(), utils.getScreenHeight()));
		fullGuid.setBackgroundDrawable(ob);
		Bitmap croppedImage = (Bitmap) getIntent().getParcelableExtra("croppedImage");
		summaryList = (ListView) findViewById(R.id.summaryList);
		summaryList.setDividerHeight(0);
		goToNextPage = (TextView) findViewById(R.id.textView2);
		
		goToNextPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ImagePageSummaryActivity.this,ShippingPageActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();
			}
		});
		Login login = Entity.query(Login.class).execute();
		List<TempImg> tempList = new ArrayList<TempImg>();
		if(login!=null){
			List<TempImage> imgList = Entity.query(TempImage.class).where("ac_token").eq(login.ac_token).executeMulti();
			if(imgList!=null && !imgList.isEmpty()){
				
				for(TempImage tmp : imgList){
					TempImg img = new TempImg();
					img.setAmt(tmp.amt!=null ? Integer.parseInt(tmp.amt) : 0);
					tempList.add(img);
					
				}
			}
		}
		
		
		LoadListAdapter adapter = new LoadListAdapter(tempList);
		summaryList.setAdapter(adapter);
	}
	public class TempImg{
		private Bitmap icon;
		private int amt;
		
		
		public Bitmap getIcon() {
			return icon;
		}
		public void setIcon(Bitmap icon) {
			this.icon = icon;
		}
		public int getAmt() {
			return amt;
		}
		public void setAmt(int amt) {
			this.amt = amt;
		}
		
		
		
	}
	public class LoadListAdapter extends BaseAdapter{
		public List<TempImg> _list;
		public LoadListAdapter(List<TempImg> list){
			this._list = list;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return _list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return _list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = inflater.inflate(R.layout.each_sumary_layout, null);
			View view_line = (View) convertView.findViewById(R.id.view_line);
			if(position==0){
				view_line.setVisibility(View.INVISIBLE);
			}
			
			
			return convertView;
		}
		
	}
}
