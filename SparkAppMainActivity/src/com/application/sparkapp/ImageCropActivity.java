package com.application.sparkapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.content.Context;
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

import com.application.sparkapp.model.Login;
import com.application.sparkapp.model.TempImage;
import com.edmodo.cropper.CropImageView;
import com.roscopeco.ormdroid.Entity;

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
    Bitmap croppedImage,bitmap;
    private CropImageView cropImageView;
    private boolean isFacebook;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image_crop);
		System.gc();
		utils = new Utils(this, this);
		RelativeLayout root_id = (RelativeLayout) findViewById(R.id.fullBack);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.signup_background, utils.getScreenWidth(), utils.getScreenHeight()));
		root_id.setBackgroundDrawable(ob);
		final String imgPath = getIntent().getStringExtra("imgPath");
		isFacebook = getIntent().getBooleanExtra("isFacebook", false);
		cropImageView = (CropImageView) findViewById(R.id.CropImageView);

		final ImageView protraitBt = (ImageView) findViewById(R.id.imageView2);
		protraitBt.setImageDrawable(getResources().getDrawable(R.drawable.crop_protreit));
		final ImageView landsBt = (ImageView) findViewById(R.id.imageView3);
		ImageView goBack = (ImageView) findViewById(R.id.imageView1);
		ImageView imgImageView = (ImageView) findViewById(R.id.ImageView_image);
		if(!isFacebook){
		bitmap = BitmapFactory.decodeFile(imgPath);
		}else{
		bitmap = DownloadImage(imgPath);	
		}
		utils.getScreenWidth();
		//bitmap = getResizedBitmap(bitmap, utils.getScreenWidth()*0.6f, utils.getScreenWidth());
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
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		cropImageView.setImageBitmap(bitmap);
		cropImageView.setAspectRatio(2,3);
		cropImageView.setFixedAspectRatio(true);
		cropImageView.setGuidelines(1);
		
		goToNextPage = (TextView) findViewById(R.id.textView2);
		goToNextPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
				Bitmap tempCrop = cropImageView.getCroppedImage();
				croppedImage = cropImageView.getCroppedImage();
				if(portraitFlag){
				croppedImage = getResizedBitmap(croppedImage, 1800, 1200);
				}else{
				croppedImage = getResizedBitmap(croppedImage, 1200, 1800);	
				}
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
				OutputStream fOut2 = null;
				OutputStream fOut3 = null;
				
				String cropName = ""+UUID.randomUUID();
				File file = new File(directory, ""+cropName+".jpg");
				File tumb = new File(directory, "tmb_"+UUID.randomUUID()+".jpg");
				File tmb = new File(directory, ""+cropName+"_tmb.jpg");
				
				fOut = new FileOutputStream(file);
				croppedImage.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
				fOut.flush();
				fOut.close();
				MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
				temp.path = file.getAbsolutePath();
				
				fOut2 = new FileOutputStream(tumb);
				bitmap = getResizedBitmap(bitmap, 100, 100);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut2);
				fOut2.flush();
				fOut2.close();
				MediaStore.Images.Media.insertImage(getContentResolver(),tumb.getAbsolutePath(),tumb.getName(),tumb.getName());
				temp.originPath = tumb.getAbsolutePath();
				
				
				fOut3 = new FileOutputStream(tmb);
				tempCrop = getResizedBitmap(tempCrop, 100, 100);
				tempCrop.compress(Bitmap.CompressFormat.JPEG, 85, fOut3);
				fOut3.flush();
				fOut3.close();
				MediaStore.Images.Media.insertImage(getContentResolver(),tmb.getAbsolutePath(),tmb.getName(),tmb.getName());
				
				temp.amt = "1";
				temp.id = temp.getPk();
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
					cropImageView.setAspectRatio(2,3);
					protraitBt.setImageDrawable(getResources().getDrawable(R.drawable.crop_protreit));
					landsBt.setImageDrawable(getResources().getDrawable(R.drawable.crop_landscape_default));
				}
			}
		});
		landsBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(portraitFlag){
					portraitFlag = false;
					cropImageView.setAspectRatio(3,2);
					protraitBt.setImageDrawable(getResources().getDrawable(R.drawable.portriet_default));
					landsBt.setImageDrawable(getResources().getDrawable(R.drawable.landscape_select));
				}
			}
		});
		goBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ImageCropActivity.this,ImageGridViewActivity.class);	
				i.putStringArrayListExtra("imgList",  getIntent().getStringArrayListExtra("IMG_LIST"));
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();				
			}
		});
	}
	@Override
	public void onBackPressed(){
//		Toast.makeText(getApplicationContext(), "Hello "+getIntent().getStringArrayListExtra("IMG_LIST").size(), Toast.LENGTH_SHORT).show();
		Intent i = new Intent(ImageCropActivity.this,ImageGridViewActivity.class);	
		i.putStringArrayListExtra("imgList",  getIntent().getStringArrayListExtra("IMG_LIST"));
		startActivity(i);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		finish();
	}
	public Bitmap getResizedBitmap(Bitmap bm, float newHeight, float newWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight =  newHeight / height;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
    private Bitmap DownloadImage(String URL)
    {        
//      System.out.println("image inside="+URL);
        Bitmap bitmap = null;
        InputStream in = null;        
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
//        System.out.println("image last");
        return bitmap;                
    }
    private InputStream OpenHttpConnection(String urlString)
            throws IOException
            {
                InputStream in = null;
                int response = -1;

                URL url = new URL(urlString);
                URLConnection conn = url.openConnection();

                if (!(conn instanceof HttpURLConnection))                    
                    throw new IOException("Not an HTTP connection");

                try{
                    HttpURLConnection httpConn = (HttpURLConnection) conn;
                    httpConn.setAllowUserInteraction(false);
                    httpConn.setInstanceFollowRedirects(true);
                    httpConn.setRequestMethod("GET");
                    httpConn.connect();

                    response = httpConn.getResponseCode();                
                    if (response == HttpURLConnection.HTTP_OK) 
                    {
                        in = httpConn.getInputStream();                                
                    }                    
                }
                catch (Exception ex)
                {
                    throw new IOException("Error connecting");            
                }
                return in;    
    }
}
