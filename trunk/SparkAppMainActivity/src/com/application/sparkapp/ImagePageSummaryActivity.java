package com.application.sparkapp;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import com.application.sparkapp.model.Login;
import com.application.sparkapp.model.TempImage;
import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ImagePageSummaryActivity extends Activity {
	private Utils utils;
	private ListView summaryList;
	private TextView goToNextPage,picCount,picTotal;
	private Activity _activity;
	private int picCt,total;
	private UserVO user;
	private int newRes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
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
		_activity = ImagePageSummaryActivity.this;
		ImageView goBack = (ImageView) findViewById(R.id.imageView1);
		
		picCount = (TextView) findViewById(R.id.textView4);
		picTotal = (TextView) findViewById(R.id.totalAmountImage);
		user = Entity.query(UserVO.class).execute();
		total = Integer.parseInt(user.numberPictureCanUpload);
		picTotal.setText("/"+total);
		
		goBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ImagePageSummaryActivity.this,MainPhotoSelectActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
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
					img.setBgicon(tmp.originPath);
					String[] temp = tmp.path.split("\\.");
					img.setCropIcon(temp[0]+"_tmb"+"."+temp[1]);
					picCt += Integer.parseInt(tmp.amt);
					tempList.add(img);
					
				}
				picCount.setText(picCt+"");
			}
			
		}				
		LoadListAdapter adapter = new LoadListAdapter(tempList);
		summaryList.setAdapter(adapter);

		
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(ImagePageSummaryActivity.this,MainPhotoSelectActivity.class);
//		i.putExtra("croppedImage", (Bitmap) getIntent().getParcelableExtra("croppedImage"));
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}
	public class TempImg{
		private String bgicon,cropIcon;
		private int amt;
		
		
		public String getBgicon() {
			return bgicon;
		}
		public void setBgicon(String bgicon) {
			this.bgicon = bgicon;
		}
		public String getCropIcon() {
			return cropIcon;
		}
		public void setCropIcon(String cropIcon) {
			this.cropIcon = cropIcon;
		}
		public int getAmt() {
			return amt;
		}
		public void setAmt(int amt) {
			this.amt = amt;
		}
		
		
		
	}
	public void updatePicAmt(int id){
		TempImage tempImage = Entity.query(TempImage.class).where("id").eq(id).execute();
		tempImage.amt = newRes+"";
		tempImage.save();
	}
	public void checkAmt(){
		
	}
	
	public class LoadListAdapter extends BaseAdapter{
		private ViewHolder viewHolder;
		public List<TempImg> _list;
		private int size;
		
		public LoadListAdapter(List<TempImg> list){
			this._list = list;
			utils = new Utils(_activity, _activity);
			size = (int) Math.ceil(Math.sqrt(utils.getScreenWidth() * utils.getScreenHeight()));
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
			if(convertView== null){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = inflater.inflate(R.layout.each_sumary_layout, null);
			View view_line = (View) convertView.findViewById(R.id.view_line);
			if(position==0){
				view_line.setVisibility(View.INVISIBLE);
			}
			viewHolder.bgImg = (ImageView) convertView.findViewById(R.id.imgBg);
			viewHolder.cropImg = (ImageView) convertView.findViewById(R.id.imageView1);
			viewHolder.amt = (TextView) convertView.findViewById(R.id.textView3);
			viewHolder.minusBt = (TextView) convertView.findViewById(R.id.textView2);
			viewHolder.plusBt = (TextView) convertView.findViewById(R.id.textView4);
			viewHolder.viewClick = (RelativeLayout) convertView.findViewById(R.id.summary_content);
			
			convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			newRes = 0;
			viewHolder.amt.setText(newRes+"");
			viewHolder.viewClick.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
			viewHolder.minusBt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(newRes>0){
						newRes--;
						viewHolder.amt.setText(""+(newRes));
						
					}
					
				}
			});
			viewHolder.plusBt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(newRes<10){
						newRes++;
						viewHolder.amt.setText(""+(newRes));
						
					}
				}
			});
			
			TempImg temp = _list.get(position);
			Bitmap bgBitmap = BitmapFactory.decodeFile(temp.getBgicon());
			viewHolder.bgImg.setImageBitmap(bgBitmap);
			
			 Bitmap myBitmap = BitmapFactory.decodeFile(temp.getCropIcon());
			viewHolder.cropImg.setImageBitmap(myBitmap);
			
			return convertView;
		}

		public class ViewHolder{
			public TextView minusBt,plusBt,amt;
			public ImageView cropImg;
			public ImageView bgImg;
			public RelativeLayout viewClick;
		}
		
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}
