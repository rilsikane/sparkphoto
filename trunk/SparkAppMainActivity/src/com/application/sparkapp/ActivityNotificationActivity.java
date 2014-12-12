package com.application.sparkapp;

import java.util.ArrayList;
import java.util.List;

import com.application.sparkapp.PerkPageActivity.LoadMoreData;
import com.application.sparkapp.dto.NotificationDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.application.sparkapp.util.DateUtil;
import com.roscopeco.ormdroid.Entity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityNotificationActivity extends Activity {
	private ListView activityList;
	private UserVO user;
	private List<NotificationDto> notificationList = new ArrayList<NotificationDto>();
	private ListAdapter adapter;
	private boolean loadingMore = false;
	private int page=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_activity_notification);
		System.gc();
		activityList = (ListView) findViewById(R.id.listView1);
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
		
		user = Entity.query(UserVO.class).where("id").eq("1").execute();
		
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ActivityNotificationActivity.this, MainPhotoSelectActivity.class);				 
		        startActivity(i);
		        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		        finish();
			}
		});
		new InitLoadData(1).execute();
		activityList.setOnScrollListener(new OnScrollListener() {
			int currentFirstVisibleItem = 0;
			int currentVisibleItemCount = 0;
			int totalItemCount = 0;
			int currentScrollState = 0;
			@Override
			public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			    this.currentFirstVisibleItem = firstVisibleItem;
			    this.currentVisibleItemCount = visibleItemCount;
			    this.totalItemCount = totalItemCount;
			}

			@Override
			public void onScrollStateChanged(AbsListView absListView, int scrollState) {
			    this.currentScrollState = scrollState;
			    this.isScrollCompleted();
			}

			private void isScrollCompleted() {
			    if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE && this.totalItemCount == (currentFirstVisibleItem + currentVisibleItemCount)) {
			        /*** In this way I detect if there's been a scroll which has completed ***/
			        /*** do the work for load more date! ***/
			        if (!loadingMore && notificationList.size()%10==0) {
			            loadingMore = true;
			            new LoadMoreData(page).execute();
			        }
			    }
			}
	    });
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(ActivityNotificationActivity.this, MainPhotoSelectActivity.class);				 
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
	}
	
	public class ListAdapter extends BaseAdapter{
		List<NotificationDto> tempList;
		public ListAdapter(List<NotificationDto> notiList){
			this.tempList = notiList;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tempList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return tempList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.each_layout_activity, null);
			TextView detailTxt = (TextView) convertView.findViewById(R.id.textView1);
			TextView statTxt = (TextView) convertView.findViewById(R.id.textView3);
			TextView statDate = (TextView) convertView.findViewById(R.id.textView4);
			View badgeColor = (View) convertView.findViewById(R.id.view2);
//			if(position==1){
//				badgeColor.setBackgroundColor(Color.GREEN);
//			}else if(position==2){
//				badgeColor.setBackgroundColor(Color.RED);
//			}
//			detailTxt.setText(temp.get(position));
			try{
			NotificationDto temp = tempList.get(position);
			detailTxt.setText(temp.getMessage());
			convertTypeToStatus(temp.getType(),statTxt,badgeColor);
			statDate.setText(DateUtil.convertStringToStringDatetimeFormat(temp.getTime_created()));
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			return convertView;
		}
		
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
    public class InitLoadData extends AsyncTask<String, Void, List<NotificationDto>>implements OnCancelListener {
			ProgressHUD mProgressHUD;
			int crPage;
			
			public InitLoadData(int page){
				this.crPage = page;
			}
			
			@Override
			protected void onPreExecute() {
				mProgressHUD = ProgressHUD.show(ActivityNotificationActivity.this,"Loading ...", true, true, this);
				super.onPreExecute();
			}
			
			@Override
			protected List<NotificationDto> doInBackground(String... params) {
				// TODO Auto-generated method stub
				 
				return JSONParserForGetList.getInstance().getListNotification(user.ac_token, crPage);
			}
			
			@Override
			protected void onPostExecute(List<NotificationDto> result) {
				super.onPostExecute(result);
				if (result != null) {
					notificationList.addAll(result);
				} else {
					mProgressHUD.dismiss();
				}
				
				adapter = new ListAdapter(notificationList);
				activityList.setAdapter(adapter);
				mProgressHUD.dismiss();
			}

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				mProgressHUD.dismiss();
			}

    	}
    public class LoadMoreData extends AsyncTask<String, Void, List<NotificationDto>>implements OnCancelListener {
		ProgressHUD mProgressHUD;
		int crPage;
		
		public LoadMoreData(int page){
			this.crPage = page;
		}
		
		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(ActivityNotificationActivity.this,"Loading ...", true, true, this);
			super.onPreExecute();
		}
		
		@Override
		protected List<NotificationDto> doInBackground(String... params) {
			// TODO Auto-generated method stub
			 
			return JSONParserForGetList.getInstance().getListNotification(user.ac_token, crPage);
		}
		
		@Override
		protected void onPostExecute(List<NotificationDto> result) {
			super.onPostExecute(result);
			if (result != null) {
				notificationList.addAll(result);
				loadingMore = false;
				page++;
			} else {
				mProgressHUD.dismiss();
			}
			
			adapter.notifyDataSetChanged();
			mProgressHUD.dismiss();
			
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}

	}
    public void convertTypeToStatus(String type,TextView statTxt,View badgeCol){
		String status="";
		int color = Color.BLUE;
    	if(Utils.isNotEmpty(type) && Utils.isNumeric(type)){
			switch (Integer.parseInt(type)) {
			case 0:
				status = "New  order";
				color = Color.BLUE;
				break;
			case 1:
				status = "Processing";
				color = Color.YELLOW;
				break;
			case 2:
				status = "Completed";
				color = Color.GREEN;
				break;
			case 3:
				status = "Rejected";
				color = Color.RED;
				break;

			default:
				break;
			}
			statTxt.setText(status);
			badgeCol.setBackgroundColor(color);
			
    	}
    	
    }
}
