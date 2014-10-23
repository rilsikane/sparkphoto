package com.application.sparkapp;

import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sparkapp.ImageListActivity.ViewHolder;
import com.application.sparkapp.util.BitmapTransform;
import com.squareup.picasso.Picasso;

@SuppressLint("NewApi")
public class GridViewImageAdapter extends BaseAdapter {

	private Activity _activity;
	private List<String> imgPaths;
	private int imageWidth;
	private Utils utils;
	private int size;
	private ViewHolder viewHolder;

	public GridViewImageAdapter(Activity activity, List<String> menus,
			int imageWidth) {
		this._activity = activity;
		this.imgPaths = menus;
		this.imageWidth = imageWidth;
		utils = new Utils(_activity, _activity);
		size = (int) Math.ceil(Math.sqrt(utils.getScreenWidth() * utils.getScreenHeight()));
	}

	@Override
	public int getCount() {
		return this.imgPaths.size();
	}

	@Override
	public Object getItem(int position) {
		return this.imgPaths.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		System.gc();
		if(convertView== null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.each_box_layout, null);
			viewHolder.imgView = (ImageView) convertView.findViewById(R.id.showCase);
			viewHolder.imgView.getLayoutParams().height = imageWidth;
			viewHolder.imgView.getLayoutParams().width = imageWidth;
			viewHolder.position = position;
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final String filename = imgPaths.get(position);
		Picasso.with(_activity).load(new File(filename))
	    .transform(new BitmapTransform(imageWidth, imageWidth))
	    .centerCrop()
        .noFade()
	    .resize(size, size).into(viewHolder.imgView);
		
		viewHolder.imgView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(_activity,ImageGuidCropActivity.class);
				i.putExtra("imgPath", filename);
				_activity.startActivity(i);
				_activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				_activity.finish();
			}
		});
		
		return convertView;
	}
	
	public class ViewHolder{
		public ImageView imgView;
		public int position;
	}
	
}
