package com.application.sparkapp;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
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

import com.application.sparkapp.dto.PerksDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;
import com.squareup.picasso.Picasso;

public class PerkPageActivity extends Activity {
	private Utils utils;
	private ListView perkList;

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
		perkList = (ListView) findViewById(R.id.listView1);
		ImageView goBack = (ImageView) findViewById(R.id.imageView1);
		final ImageView premim = (ImageView) findViewById(R.id.imageView2);
		final ImageView regular = (ImageView) findViewById(R.id.imageView3);
		
		String type = getIntent().hasExtra("type") ? getIntent().getStringExtra("type") : "2";
		
		if("2".equals(type)){
			premim.setImageDrawable(getResources().getDrawable(R.drawable.premium_selected));
			regular.setImageDrawable(getResources().getDrawable(R.drawable.regular_default));
		}else{
			regular.setImageDrawable(getResources().getDrawable(R.drawable.regular_selected));
			premim.setImageDrawable(getResources().getDrawable(R.drawable.premium_default));
		}
		new InitAndLoadData().execute(type);
		premim.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				ListViewAdapter adapter = new ListViewAdapter();
//				perkList.setAdapter(adapter);
				premim.setImageDrawable(getResources().getDrawable(R.drawable.premium_selected));
				regular.setImageDrawable(getResources().getDrawable(R.drawable.regular_default));
				new InitAndLoadData().execute("2");
			}
		});
		regular.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				ListViewAdapter adapter = new ListViewAdapter();
//				perkList.setAdapter(adapter);
				regular.setImageDrawable(getResources().getDrawable(R.drawable.regular_selected));
				premim.setImageDrawable(getResources().getDrawable(R.drawable.premium_default));
				new InitAndLoadData().execute("1");
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
			UserVO user = Entity.query(UserVO.class).where("id").eq(1).execute();
			List<PerksDto> perkList = new ArrayList<PerksDto>();
			if(user!=null){
				perkList = JSONParserForGetList.getInstance().getListPerks(user.ac_token,params[0]);
			}
			return perkList;
		}

		@Override
		protected void onPostExecute(List<PerksDto> result) {
			super.onPostExecute(result);
			ListViewAdapter adapter = new ListViewAdapter(result);
			perkList.setAdapter(adapter);
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
				convertView = inflater.inflate(R.layout.each_perk_layout, null);
				viewHolder.click = (RelativeLayout) convertView.findViewById(R.id.perkClick);
				viewHolder.perkName = (TextView) convertView.findViewById(R.id.perkName);
				Typeface face = Typeface.createFromAsset(getAssets(),"fonts/ThaiSansNeue-Bold.ttf");
				viewHolder.perkName.setTypeface(face);
				viewHolder.expire = (TextView) convertView.findViewById(R.id.textView2);
				viewHolder.sponsorName = (TextView) convertView.findViewById(R.id.textView3);
				viewHolder.perImg = (ImageView) convertView.findViewById(R.id.imageView1);
				viewHolder.gift = (ImageView) convertView.findViewById(R.id.imageView2);
				
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
			Intent i = new Intent(PerkPageActivity.this,PerkDetailMainActivity.class);
			i.putExtra("perksDto",dto);
			startActivity(i);
			finish();
		}
		
	}
	
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}