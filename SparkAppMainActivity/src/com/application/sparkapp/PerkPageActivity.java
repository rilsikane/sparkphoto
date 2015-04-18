package com.application.sparkapp;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.application.sparkapp.dto.NotificationDto;
import com.application.sparkapp.dto.PerksDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.application.sparkapp.util.GlobalVariable;
import com.roscopeco.ormdroid.Entity;
import com.squareup.picasso.Picasso;

public class PerkPageActivity extends Activity {
	private Utils utils;
	private ListView perkList;
	private UserVO user;
	private List<PerksDto> perkDataList;
	private int page=1;
	private String type;
	private ListViewAdapter adapter;
	private boolean loadingMore = false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_perk_page);

		System.gc();
		utils = new Utils(this, this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(),R.drawable.setting_page, utils.getScreenWidth(),utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		perkList = (ListView) findViewById(R.id.listView1);
		ImageView goBack = (ImageView) findViewById(R.id.imageView1);
		final ImageView premim = (ImageView) findViewById(R.id.imageView2);
		final ImageView regular = (ImageView) findViewById(R.id.imageView3);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/ThaiSansNeue-Regular.ttf");
		TextView perkHeader = (TextView) findViewById(R.id.textView1);
		perkHeader.setTypeface(tf);
		
		type = getIntent().hasExtra("type") ? getIntent().getStringExtra("type") : "2";
		
		if("2".equals(type)){
			premim.setImageDrawable(getResources().getDrawable(R.drawable.premium_selected));
			regular.setImageDrawable(getResources().getDrawable(R.drawable.regular_default));
		}else{
			regular.setImageDrawable(getResources().getDrawable(R.drawable.regular_selected));
			premim.setImageDrawable(getResources().getDrawable(R.drawable.premium_default));
		}
		
		user = Entity.query(UserVO.class).where("id=1").execute();
		new InitAndLoadData().execute(type);
		perkList.setOnScrollListener(new OnScrollListener() {
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
			        if (!loadingMore && perkDataList.size()%10==0) {
			            loadingMore = true;
			            new LoadMoreData().execute();
			        }
			    }
			}

	    });
		
		premim.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				ListViewAdapter adapter = new ListViewAdapter();
//				perkList.setAdapter(adapter);
				page=1;
				premim.setImageDrawable(getResources().getDrawable(R.drawable.premium_selected));
				regular.setImageDrawable(getResources().getDrawable(R.drawable.regular_default));
				new InitAndLoadData().execute("2");
				type = "2";
			}
		});
		regular.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				ListViewAdapter adapter = new ListViewAdapter();
//				perkList.setAdapter(adapter);
				page=1;
				regular.setImageDrawable(getResources().getDrawable(R.drawable.regular_selected));
				premim.setImageDrawable(getResources().getDrawable(R.drawable.premium_default));
				new InitAndLoadData().execute("1");
				type = "1";
			}
		});
		goBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(PerkPageActivity.this,MainPhotoSelectActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(PerkPageActivity.this,MainPhotoSelectActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}
	public class InitAndLoadData extends AsyncTask<String, Void, List<PerksDto>>
	implements OnCancelListener {
		ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(PerkPageActivity.this,
					"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected List<PerksDto> doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			perkDataList = new ArrayList<PerksDto>();
			if(user!=null){ 
				perkDataList = JSONParserForGetList.getInstance().getListPerks(user.ac_token,params[0],"1");
			}else{
				perkDataList = JSONParserForGetList.getInstance().getListPerks(null,params[0],"1");
			}
			return perkDataList;
		}

		@Override
		protected void onPostExecute(List<PerksDto> result) {
			super.onPostExecute(result);
			adapter = new ListViewAdapter(result);
			perkList.setAdapter(adapter);
			mProgressHUD.dismiss();
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}

	}
	
	public class LoadMoreData extends AsyncTask<String, Void, List<PerksDto>>
	implements OnCancelListener {
		ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(PerkPageActivity.this,
					"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected List<PerksDto> doInBackground(String... params) {
			
			return JSONParserForGetList.getInstance().getListPerks(user.ac_token,type,(page+1)+"");
		}

		@Override
		protected void onPostExecute(List<PerksDto> result) {
			super.onPostExecute(result);
			 if (result != null) {
      		   perkDataList.addAll(result);
      		   page++;
      		   loadingMore = false;
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
	
	
	public class ListViewAdapter extends BaseAdapter{
		private List<PerksDto> listPerks;
		private ViewHolder viewHolder;
		public ListViewAdapter(List<PerksDto> listPerks){
			this.listPerks = listPerks;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listPerks!=null ? listPerks.size() :0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listPerks.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView== null){
				viewHolder = new ViewHolder();
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				if(utils.getScreenWidth()<=480&&utils.getScreenHeight()<=800){
					convertView = inflater.inflate(R.layout.each_perk_small_layout, null);
				}else{				
					convertView = inflater.inflate(R.layout.each_perk_layout, null);
				}
				
				viewHolder.click = (RelativeLayout) convertView.findViewById(R.id.perkClick);
				viewHolder.perkName = (TextView) convertView.findViewById(R.id.perkName);
				Typeface face = Typeface.createFromAsset(getAssets(),"fonts/ThaiSansNeue-Bold.ttf");
				viewHolder.perkName.setTypeface(face);
				viewHolder.perkName.setIncludeFontPadding(false);
				viewHolder.expire = (TextView) convertView.findViewById(R.id.textView2);
				viewHolder.sponsorName = (TextView) convertView.findViewById(R.id.textView3);
				viewHolder.perImg = (ImageView) convertView.findViewById(R.id.imageView1);
				viewHolder.gift = (ImageView) convertView.findViewById(R.id.imageView2);
				viewHolder.gift.setVisibility(View.INVISIBLE);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			PerksDto model = listPerks.get(position);
			
			viewHolder.perkName.setText(model.getName());
			viewHolder.expire.setText(model.getTimeExpire());
			viewHolder.sponsorName.setText(model.getBrandname());
			Picasso.with(getApplicationContext()).load(model.getThumnailImages()).into(viewHolder.perImg);
			if(model.getUsed()){
				Picasso.with(getApplicationContext()).load(R.drawable.redeem_icon).into(viewHolder.gift);	
			}else{
				Picasso.with(getApplicationContext()).load(R.drawable.gift_icon).into(viewHolder.gift);		
			}
			
			
			viewHolder.click.setOnClickListener(new OnSelectPerksListener(position,model));
			
			
			return convertView;
		}
		public class ViewHolder{
			public TextView perkName,expire,sponsorName;
			public ImageView perImg,gift;
			public RelativeLayout click;
			
		}
		
	}
	public class OnSelectPerksListener implements OnClickListener{
		private int _position;
		private PerksDto dto;
		public OnSelectPerksListener(int position,PerksDto dto){
			this._position = position;
			this.dto = dto;
		}
		
		@Override
		public void onClick(View v) {
			final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(PerkPageActivity.this);
			if(prefs.getString("BACK_REGISTER_PAGE_STATE", "").equalsIgnoreCase(new GlobalVariable().REGISTER_COME_FROM_PAGE_REGIS_LATER)){
				AlertDialog.Builder emailBuilder = new AlertDialog.Builder(PerkPageActivity.this);
				emailBuilder.setCancelable(true);
				emailBuilder.setMessage("Please register to SPARK before using this function.");
				emailBuilder.setPositiveButton("Register",new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    Intent intent = new Intent(PerkPageActivity.this,SignUpPageOneMainActivity.class);
	                    startActivity(intent);
	                    finish();
	                    prefs.edit().remove("BACK_REGISTER_PAGE_STATE").commit();
	                }
	            });
				emailBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
	            AlertDialog showAlerEmail = emailBuilder.create();
	            showAlerEmail.show();
			}else{
				JSONParserForGetList.getInstance().viewPerk(user.ac_token, dto.getId());
				Intent i = new Intent(PerkPageActivity.this,PerkDetailMainActivity.class);
				i.putExtra("perksDto",dto);
				startActivity(i);
				finish();
			}
		}
		
	}
	
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
//    }
}