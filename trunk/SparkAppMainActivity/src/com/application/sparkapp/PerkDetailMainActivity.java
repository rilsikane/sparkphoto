package com.application.sparkapp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sparkapp.dto.PerksDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class PerkDetailMainActivity extends Activity {
	private Utils utils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_perk_detail_main);
		System.gc();
		utils = new Utils(this, this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(),R.drawable.setting_page, utils.getScreenWidth(),utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		ImageView sponserImage = (ImageView) findViewById(R.id.imageView3);
		ImageView perksImage = (ImageView) findViewById(R.id.imageView2);
		TextView perksName = (TextView) findViewById(R.id.perkName);
		TextView dueDate = (TextView) findViewById(R.id.duedate);
		TextView perk_detail = (TextView) findViewById(R.id.perk_detail);
		RelativeLayout reedem = (RelativeLayout) findViewById(R.id.reedem);
		 
		
		
		final PerksDto perksDto = getIntent().getExtras().getParcelable("perksDto");
		Picasso.with(getApplicationContext()).load(perksDto.getCoverImages()).into(perksImage);
		perksName.setText(perksDto.getName());
		dueDate.setText(perksDto.getTimeExpire());
		perk_detail.setText(perksDto.getDescription());
		if(!perksDto.getUsed()){
			//reedem.setBackgroundColor(Color.BLACK);
			reedem.setVisibility(View.GONE);
//			Picasso.with(getApplicationContext()).load(R.drawable.redeem).into(reedem);
			
		}
		
		URL url_value;
		try {
			url_value = new URL(perksDto.getThumnailImages());
			Bitmap mIcon1 = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
			sponserImage.setImageBitmap(new CircleTransform().transform(mIcon1));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
		backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(PerkDetailMainActivity.this,PerkPageActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
		reedem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText input = new EditText(PerkDetailMainActivity.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
				input.setLayoutParams(lp);
				AlertDialog.Builder builder1 = new AlertDialog.Builder(
						PerkDetailMainActivity.this);
				builder1.setMessage("Put your reedeem code");
				builder1.setCancelable(true);
				builder1.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
							String acCode = Entity.query(UserVO.class).where("id").eq(1).execute().ac_token;
							final UserDto dto = JSONParserForGetList.getInstance().ReedeemCode(input.getText().toString(), perksDto.getId(), acCode);
								if(dto!=null){
									AlertDialog.Builder builder1 = new AlertDialog.Builder(
											PerkDetailMainActivity.this);
									builder1.setMessage("Code is redeemed sucessfully. Please enjoy your extra print.");
									builder1.setCancelable(true);
									builder1.setPositiveButton("Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,
														int id) {
													UserVO user = Entity.query(UserVO.class).where("id").eq(1).execute();
													user = user.convertDtoToVo(dto);
													 user.id = 1;
													 user.save();
													dialog.cancel();
												}
											});
									AlertDialog alert11 = builder1.create();
									alert11.show();
								}else{
									AlertDialog.Builder builder1 = new AlertDialog.Builder(
											PerkDetailMainActivity.this);
									builder1.setMessage("Code is invaild. Please enter a vaild code.");
									builder1.setCancelable(true);
									builder1.setPositiveButton("Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,
														int id) {
													dialog.cancel();
												}
											});
									AlertDialog alert11 = builder1.create();
									alert11.show();
								}
							}
						});
				
				AlertDialog alert11 = builder1.create();
				alert11.setView(input);
				alert11.show();
			}
		});
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
    @Override
    public void onBackPressed(){
    	Intent i = new Intent(PerkDetailMainActivity.this,PerkPageActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
    }
    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
     
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
     
            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }
     
            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
     
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);
     
            float r = size/2f;
            canvas.drawCircle(r, r, r, paint);
     
            squaredBitmap.recycle();
            return bitmap;
        }

		@Override
		public String key() {
			// TODO Auto-generated method stub
			return "circle";
		}
    }
}
