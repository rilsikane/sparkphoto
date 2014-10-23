package com.application.sparkapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.application.sparkapp.model.Login;
import com.application.sparkapp.model.TempImage;
import com.edmodo.cropper.CropImageView;
import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageCropActivity extends Activity {
	private Utils utils;
	private TextView goToNextPage;
	// Static final constants
    private static final int DEFAULT_ASPECT_RATIO_VALUES = 10;
    private static final int ROTATE_NINETY_DEGREES = 90;
    private static final String ASPECT_RATIO_X = "ASPECT_RATIO_X";
    private static final String ASPECT_RATIO_Y = "ASPECT_RATIO_Y";
    private static final int ON_TOUCH = 1;

    // Instance variables
    private int mAspectRatioX = DEFAULT_ASPECT_RATIO_VALUES;
    private int mAspectRatioY = DEFAULT_ASPECT_RATIO_VALUES;
    private boolean portraitFlag = true;
    Bitmap croppedImage;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image_crop);
		System.gc();
		utils = new Utils(this, this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.fullBack);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.signup_background, utils.getScreenWidth(), utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		final String imgPath = getIntent().getStringExtra("imgPath");
		
		final CropImageView cropImageView = (CropImageView) findViewById(R.id.CropImageView);
		ImageView protraitBt = (ImageView) findViewById(R.id.imageView2);
		ImageView landsBt = (ImageView) findViewById(R.id.imageView3);
		cropImageView.getLayoutParams().height = utils.getScreenHeight()-utils.dpToPx(140);
		
		
		Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
		try{
		ExifInterface exif = new ExifInterface(imgPath);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        Matrix matrix = new Matrix();
        if (orientation == 6) {
            matrix.postRotate(90);
        }
        else if (orientation == 3) {
            matrix.postRotate(180);
        }
        else if (orientation == 8) {
            matrix.postRotate(270);
        }
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		cropImageView.setImageBitmap(bitmap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		goToNextPage = (TextView) findViewById(R.id.textView2);
		goToNextPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
				croppedImage = cropImageView.getCroppedImage();
				Login login = Entity.query(Login.class).execute();
				TempImage temp = new TempImage();
				temp.ac_token = login.ac_token;
				temp.originPath = imgPath;
				File directory = new File(
						Environment.getExternalStorageDirectory()
								+ "/Spark/temp_image/");
				if (!directory.exists()) {
					directory.mkdirs();
				}
				OutputStream fOut = null;
				File file = new File(directory, ""+UUID.randomUUID()+".jpg");
				fOut = new FileOutputStream(file);
				croppedImage.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
				fOut.flush();
				fOut.close();
				MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
				temp.path = directory.getAbsolutePath()+""+UUID.randomUUID()+".jpg";
				temp.save();
				
				Intent i = new Intent(ImageCropActivity.this,GuideTotalPrintActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		protraitBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!portraitFlag){
					portraitFlag = true;
					cropImageView.rotateImage(270);
				}
			}
		});
		landsBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(portraitFlag){
					portraitFlag = false;
					cropImageView.rotateImage(ROTATE_NINETY_DEGREES);
				}
			}
		});
	}
	

}
