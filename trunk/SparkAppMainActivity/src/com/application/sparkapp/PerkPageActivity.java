package com.application.sparkapp;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.PerksDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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

public class PerkPageActivity extends Activity {
	private Utils utils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_perk_page);

		System.gc();
		utils = new Utils(this, this);
		utils = new Utils(getApplicationContext(), this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(),R.drawable.setting_page, utils.getScreenWidth(),utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		final ListView perkList = (ListView) findViewById(R.id.listView1);
		ListViewAdapter adapter = new ListViewAdapter();
		perkList.setAdapter(adapter);
		ImageView goBack = (ImageView) findViewById(R.id.imageView1);
		final ImageView premim = (ImageView) findViewById(R.id.imageView2);
		final ImageView regular = (ImageView) findViewById(R.id.imageView3);
		premim.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ListViewAdapter adapter = new ListViewAdapter();
				perkList.setAdapter(adapter);
				premim.setImageDrawable(getResources().getDrawable(R.drawable.premium_selected));
				regular.setImageDrawable(getResources().getDrawable(R.drawable.regular_default));
			}
		});
		regular.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ListViewAdapter adapter = new ListViewAdapter();
				perkList.setAdapter(adapter);
				regular.setImageDrawable(getResources().getDrawable(R.drawable.regular_selected));
				premim.setImageDrawable(getResources().getDrawable(R.drawable.premium_default));
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
			UserVO user = Entity.query(UserVO.class).execute();
			return JSONParserForGetList.getInstance().getListPerks(user.ac_token);
		}

		@Override
		protected void onPostExecute(List<PerksDto> result) {
			super.onPostExecute(result);

		}

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}

	}
	
	
	public class ListViewAdapter extends BaseAdapter{
		List<TempPerk> temp = new ArrayList<TempPerk>();
		public ListViewAdapter(){
			TempPerk tempP = new TempPerk();
			tempP.setGift(true);
			tempP.setPerkExpire("30 Aug 2014");
			tempP.setPerkImage("");
			tempP.setPerkName("10% off on total bill");
			tempP.setSponsorName("SPONSOR NAME");
			temp.add(tempP);
			
			tempP.setGift(true);
			tempP.setPerkExpire("30 Aug 2014");
			tempP.setPerkImage("");
			tempP.setPerkName("Exclusive dining privileges");
			tempP.setSponsorName("SPONSOR NAME");
			temp.add(tempP);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return temp.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return temp.get(position);
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
			convertView = inflater.inflate(R.layout.each_perk_layout, null);
			TempPerk model = temp.get(position);
			TextView perkName = (TextView) convertView.findViewById(R.id.perkName);
			TextView expire = (TextView) convertView.findViewById(R.id.textView2);
			TextView sponsorName = (TextView) convertView.findViewById(R.id.textView3);
			ImageView perImg = (ImageView) convertView.findViewById(R.id.imageView1);
			perkName.setText(model.getPerkName());
			expire.setText(model.getPerkExpire());
			sponsorName.setText(model.getSponsorName());
			Picasso.with(getApplicationContext()).load(R.drawable.pepper_p).into(perImg);
			return convertView;
		}
		
	}
	public class TempPerk{
		private String perkName;
		private String perkImage;
		private String perkExpire;
		private String sponsorName;
		private boolean isGift;
		public String getPerkName() {
			return perkName;
		}
		public void setPerkName(String perkName) {
			this.perkName = perkName;
		}
		public String getPerkImage() {
			return perkImage;
		}
		public void setPerkImage(String perkImage) {
			this.perkImage = perkImage;
		}
		public String getPerkExpire() {
			return perkExpire;
		}
		public void setPerkExpire(String perkExpire) {
			this.perkExpire = perkExpire;
		}
		public String getSponsorName() {
			return sponsorName;
		}
		public void setSponsorName(String sponsorName) {
			this.sponsorName = sponsorName;
		}
		public boolean isGift() {
			return isGift;
		}
		public void setGift(boolean isGift) {
			this.isGift = isGift;
		}			
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}