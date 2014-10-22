package com.application.sparkapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageGridViewActivity extends Activity {
	private Utils utils;
	private int columnWidth;
	private GridView gridView;
	private GridViewImageAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
				i.putExtra("facebookUserId", getIntent().getStringExtra("facebookUserId"));
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
		
		
		gridView = (GridView) findViewById(R.id.gridView1);
		InitilizeGridLayout();		
		ArrayList<String> tempList = new ArrayList<String>();
		for(int i= 0;i<10;i++){
			tempList.add("http://static.tumblr.com/e9508858279f2144c7e6f4df14660fc8/ogbqjed/9eBmmp0ub/tumblr_static_wild_life_wallpaper_17942.jpg");
			tempList.add("http://121clicks.com/wp-content/uploads/2012/10/wildlife_nature_06.jpg");
			tempList.add("http://wolfhollowwildlife.org/wp/wp-content/uploads/2011/02/saw-whet-owl.jpg");
			tempList.add("http://webneel.com/daily/sites/default/files/images/daily/02-2013/23-best-wildlife-photography-meercats.jpg");
			tempList.add("http://2.bp.blogspot.com/-qpGtZjHOrRI/TxfXa8LbLJI/AAAAAAAAAkI/QuLqHxgo3Kw/s1600/Elephant-WildLife+-Wallpaper-HDTV-1080p.jpg");
		}
		
		
		adapter = new GridViewImageAdapter(this, tempList,columnWidth);
		gridView.setAdapter(adapter);
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
		i.putExtra("facebookUserId", getIntent().getStringExtra("facebookUserId"));
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}
}
