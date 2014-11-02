package com.application.sparkapp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
		
		URL url_value;
		try {
			url_value = new URL("http://a.u1sf.com/j/2427473/5d10613e669.jpg");
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
