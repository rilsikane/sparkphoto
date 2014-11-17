package com.application.sparkapp;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.application.sparkapp.model.UserVO;
import com.application.sparkapp.util.BitmapTransform;
import com.roscopeco.ormdroid.Entity;
import com.squareup.picasso.Picasso;

@SuppressLint("NewApi")
public class GridViewImageAdapter extends BaseAdapter {

	private Activity _activity;
	private ArrayList<String> imgPaths;
	private int imageWidth;
	private Utils utils;
	private int size;
	private ViewHolder viewHolder;
	private boolean isFacebook;

	public GridViewImageAdapter(Activity activity, ArrayList<String> menus,
			int imageWidth,boolean isFacebook) {
		this._activity = activity;
		this.imgPaths = menus;
		this.imageWidth = imageWidth;
		utils = new Utils(_activity, _activity);
		size = (int) Math.ceil(Math.sqrt(utils.getScreenWidth() * utils.getScreenHeight()));
		this.isFacebook = isFacebook;
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
		if(!isFacebook){
		Picasso.with(_activity).load(new File(filename))
	    .transform(new BitmapTransform(imageWidth, imageWidth))
	    .centerCrop()
        .noFade()
	    .resize(size, size).into(viewHolder.imgView);
		}else{
			Picasso.with(_activity).load(filename)
		    .transform(new BitmapTransform(imageWidth, imageWidth))
		    .centerCrop()
	        .noFade()
		    .resize(size, size).into(viewHolder.imgView);	
		}
		viewHolder.imgView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserVO user = Entity.query(UserVO.class).where("id").eq("1").execute();
				String tutorial = "";
				if(user!=null){
					tutorial = user.tutorial;
				}
				
				Intent i = new Intent(_activity,ImageGuidCropActivity.class);
				if("A".equals(tutorial)){
					i = new Intent(_activity,ImageCropActivity.class);
				}
				i.putExtra("imgPath", filename);
				i.putStringArrayListExtra("IMG_LIST", imgPaths);
				i.putExtra("isFacebook", isFacebook);
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
