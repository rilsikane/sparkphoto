package com.application.sparkapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.os.AsyncTask;
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

import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.TempImage;
import com.application.sparkapp.model.TempUserVO;
import com.application.sparkapp.model.UserVO;
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
    private TempImage temp;
    private String cropName;
    private Bitmap tempCrop;
    private File directory;
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
			
			changeSmallSizeImg();
		
		
			if(bitmap.getWidth()>3984){
				bitmap = getResizedBitmap(bitmap, new Float(bitmap.getHeight()*0.8), new Float(bitmap.getWidth()*0.8));
			}
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
					tempCrop = cropImageView.getCroppedImage();
					croppedImage = cropImageView.getCroppedImage();
					if(portraitFlag){
						croppedImage = getResizedBitmap(croppedImage, 1800, 1200);
					}else{
						croppedImage = getResizedBitmap(croppedImage, 1200, 1800);	
					}
					
					UserVO userVO = Entity.query(UserVO.class).where("id").eq(1).execute();
					
					temp = new TempImage();
					if(userVO!=null){
						temp.ac_token = userVO.ac_token;		
					}else{
						temp.ac_token = null;
					}
															
					temp.originPath = imgPath;
					directory = new File(Environment.getExternalStorageDirectory()+ "/Spark/temp_image/");
					if (!directory.exists()) {
						directory.mkdirs();
					}
					OutputStream fOut = null;
					
					cropName = ""+UUID.randomUUID();
					File file = new File(directory, ""+cropName+".jpg");									
					fOut = new FileOutputStream(file);
					new InitAndLoadData(croppedImage, fOut,file).execute();					
					
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
				Intent i = null;
				//verify from capture with camera
				if(getIntent().hasExtra("ActivtyPageFrom")){
					if(getIntent().getStringExtra("ActivtyPageFrom").equals("MainPhotoSelect")){
						i= new Intent(ImageCropActivity.this,MainPhotoSelectActivity.class);	
					}else if(getIntent().getStringExtra("ActivtyPageFrom").equals("ImagePageSummary")){
						i= new Intent(ImageCropActivity.this,ImagePageSummaryActivity.class);
					}
				}else{
					i= new Intent(ImageCropActivity.this,ImageGridViewActivity.class);
					i.putStringArrayListExtra("imgList",  getIntent().getStringArrayListExtra("imgList"));
					i.putExtra("LOAD_STATE", getIntent().getStringExtra("LOAD_STATE"));
					i.putExtra("facebookUserId", getIntent().getStringExtra("facebookUserId"));
				}
												
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();				
			}
		});
	}
	@Override
	public void onBackPressed(){
		Intent i = null;
		//verify from capture with camera
		if(getIntent().hasExtra("ActivtyPageFrom")){
			if(getIntent().getStringExtra("ActivtyPageFrom").equals("MainPhotoSelect")){
				i= new Intent(ImageCropActivity.this,MainPhotoSelectActivity.class);	
			}else if(getIntent().getStringExtra("ActivtyPageFrom").equals("ImagePageSummary")){
				i= new Intent(ImageCropActivity.this,ImagePageSummaryActivity.class);
			}
		}else{
			i= new Intent(ImageCropActivity.this,ImageGridViewActivity.class);
			i.putStringArrayListExtra("imgList",  getIntent().getStringArrayListExtra("imgList"));
		}
										
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
    private InputStream OpenHttpConnection(String urlString)throws IOException{
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
    public class InitAndLoadData extends AsyncTask<String, Void, Boolean> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		Bitmap bitmap;
		OutputStream fOut;
		File file;
		public InitAndLoadData(Bitmap bit,OutputStream fOuts,File file){
			this.bitmap = bit;
			this.fOut = fOuts;
			this.file = file;
		}
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(ImageCropActivity.this,"Loading ...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub			
			
			return bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {							
				try {
					fOut.flush();
					fOut.close();
					//MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
					temp.path = file.getAbsolutePath();
					
					OutputStream fOut2 = null;
					OutputStream fOut3 = null;
					File tumb = new File(directory, "tmb_"+UUID.randomUUID()+".jpg");
					File tmb = new File(directory, ""+cropName+"_tmb.jpg");
					
					fOut2 = new FileOutputStream(tumb);
					if(portraitFlag){
						bitmap = getResizedBitmap(bitmap, 180, 120);
					}else{
						bitmap = getResizedBitmap(bitmap, 120, 180);	
					}
					bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut2);
					fOut2.flush();
					fOut2.close();
					//MediaStore.Images.Media.insertImage(getContentResolver(),tumb.getAbsolutePath(),tumb.getName(),tumb.getName());
					temp.originPath = tumb.getAbsolutePath();
					
					fOut3 = new FileOutputStream(tmb);
					if(portraitFlag){
						tempCrop = getResizedBitmap(bitmap, 180, 120);
						}else{
						tempCrop = getResizedBitmap(bitmap, 120, 180);	
					}
					tempCrop.compress(Bitmap.CompressFormat.JPEG, 85, fOut3);
					fOut3.flush();
					fOut3.close();
					//MediaStore.Images.Media.insertImage(getContentResolver(),tmb.getAbsolutePath(),tmb.getName(),tmb.getName());
					
					temp.amt = "1";
					temp.id = temp.getPk();				
					temp.save();
					
					UserVO user = Entity.query(UserVO.class).where("id").eq("1").execute();
					String tutorial = "";
					if(user!=null){
						tutorial = user.tutorial;
					}else{
						TempUserVO tempUserVO = Entity.query(TempUserVO.class).where("id").eq("1").execute();
						tutorial = tempUserVO.tutorial;
					}
					Intent i =null; 
//					new Intent(ImageCropActivity.this,GuideTotalPrintActivity.class);
					if("A".equals(tutorial)){
						i = new Intent(ImageCropActivity.this,ImagePageSummaryActivity.class);
					}else{
						i = new Intent(ImageCropActivity.this,GuideTotalPrintActivity.class);
					}
					startActivity(i);
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					finish();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}								
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
    	public void changeSmallSizeImg(){
    		if(bitmap.getWidth()>bitmap.getHeight()){
				if(bitmap.getWidth()<1800){
					bitmap = getResizedBitmap(bitmap, new Float(bitmap.getHeight()*1.5), new Float(bitmap.getWidth()*1.5));
					if(bitmap.getWidth()<1800){
						changeSmallSizeImg();
					}
				}
			}else if(bitmap.getWidth()<bitmap.getHeight()){
				if(bitmap.getHeight()<1800){
					bitmap = getResizedBitmap(bitmap, new Float(bitmap.getHeight()*1.5), new Float(bitmap.getWidth()*1.5));
					if(bitmap.getHeight()<1800){
						changeSmallSizeImg();
					}
				}
			}
    	}
}
