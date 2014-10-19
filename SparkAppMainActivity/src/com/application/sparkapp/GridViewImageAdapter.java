package com.application.sparkapp;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

@SuppressLint("NewApi")
public class GridViewImageAdapter extends BaseAdapter {

	private Activity _activity;
	private List<String> imgPaths;
	private int imageWidth;

	public GridViewImageAdapter(Activity activity, List<String> menus,
			int imageWidth) {
		this._activity = activity;
		this.imgPaths = menus;
		this.imageWidth = imageWidth;
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

		convertView = inflater.inflate(R.layout.each_box_layout, null);
		ImageView imgView = (ImageView) convertView.findViewById(R.id.showCase);
		imgView.getLayoutParams().height = imageWidth;
		imgView.getLayoutParams().width = imageWidth;
		
		Picasso.with(_activity).load(imgPaths.get(position)).into(imgView);
		
		return convertView;
	}

}
