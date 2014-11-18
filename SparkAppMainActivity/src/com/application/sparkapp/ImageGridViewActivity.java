package com.application.sparkapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.application.sparkapp.ImageListActivity.LoadListAdapter;
import com.application.sparkapp.ImageListActivity.TempListContentView;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageGridViewActivity extends Activity {
	private Utils utils;
	private int columnWidth;
	private GridView gridView;
	private GridViewImageAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image_grid_view);
		System.gc();
		utils = new Utils(this, this);
		
		ImageView backToPrevious = (ImageView) findViewById(R.id.imageView1);
				
		backToPrevious.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ImageGridViewActivity.this,ImageListActivity.class);
				
				if(getIntent().hasExtra("facebookUserId")){
					i.putExtra("facebookUserId", getIntent().getStringExtra("facebookUserId"));
				}
				i.putExtra("LOAD_STATE", "imgGal");
				i.putExtra("loadImageState", 1);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
		
		
		gridView = (GridView) findViewById(R.id.gridView1);
		InitilizeGridLayout();		
		new InitAndLoadData().execute();
		
	}

	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,AppConstant.GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((utils.getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);

		gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding,(int) padding);
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}
	public class AppConstant {

		// Number of columns of Grid View
		public static final int NUM_OF_COLUMNS = 3;

		// Gridview image padding
		public static final int GRID_PADDING =1; // in dp

		// SD card image directory
		public static final String PHOTO_ALBUM = "NAT";

		// supported file formats
		public final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg","png");
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(ImageGridViewActivity.this,ImageListActivity.class);
		if(getIntent().hasExtra("facebookUserId")){
			i.putExtra("facebookUserId", getIntent().getStringExtra("facebookUserId"));
			String faceUID = getIntent().getStringExtra("facebookUserId");
			
		}
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
    
    public class InitAndLoadData extends AsyncTask<String, Void, ArrayList<String>> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		public InitAndLoadData(){
		}
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(ImageGridViewActivity.this,"Loading ...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected ArrayList<String> doInBackground(String... params) {
			// TODO Auto-generated method stub
				
			
			return getIntent().getStringArrayListExtra("imgList");
		}
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			if (result != null) {
				adapter = new GridViewImageAdapter(ImageGridViewActivity.this, result,columnWidth,getIntent().getBooleanExtra("isFacebook", false));
				gridView.setAdapter(adapter);
				mProgressHUD.dismiss();
			} else {
				
				mProgressHUD.dismiss();
			}
			
		}
		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}


	}
}
