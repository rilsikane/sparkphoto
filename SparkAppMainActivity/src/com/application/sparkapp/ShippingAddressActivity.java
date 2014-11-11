package com.application.sparkapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sparkapp.EmailLoginActivity.InitAndLoadData;
import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.TempImage;
import com.application.sparkapp.model.UserVO;
import com.roscopeco.ormdroid.Entity;

public class ShippingAddressActivity extends Activity {
	private Utils utils;
	private ImageView goToPreviousPage;
	private TextView confirmBtn;
	private ProgressWheel pw_two;
	boolean running;
	private EditText address_block,address_street_name,address_unit_number,address_postal;
	List<TempImage> imgList;
	int progress = 0;
	private UserVO user;
	private List<String> fileList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_shipping_address);
		System.gc();
		utils = new Utils(this, this);
		RelativeLayout fullGuid = (RelativeLayout) findViewById(R.id.imageGuid);
		
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.address_background, utils.getScreenWidth(), utils.getScreenHeight()));
		fullGuid.setBackgroundDrawable(ob);
		goToPreviousPage = (ImageView) findViewById(R.id.imageView1);
		confirmBtn = (TextView) findViewById(R.id.textView2);			
		address_block = (EditText) findViewById(R.id.editText3);
		address_street_name = (EditText) findViewById(R.id.editText4);
		address_unit_number = (EditText) findViewById(R.id.editText7);
		address_postal = (EditText) findViewById(R.id.editText8);
        
		user = Entity.query(UserVO.class).where("id").eq(1).execute();
		if(user!=null){
			address_block.setText(user.address_block);
			address_street_name.setText(user.address_street_name);
			address_unit_number.setText(user.address_unit_number);
			address_postal.setText(user.address_postal);
			imgList = Entity.query(TempImage.class).where("ac_token").eq(user.ac_token).executeMulti();
		}
		
		confirmBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(ShippingAddressActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.custom_loading_dialog);
				RelativeLayout closeDialog =  (RelativeLayout) dialog.findViewById(R.id.close_dialog_layout);
				pw_two = (ProgressWheel) dialog.findViewById(R.id.progressBarTwo);
				
				closeDialog.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						
					}
				});
				new UploadImage(dialog).execute();
				
				
				
			}
		});
		goToPreviousPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ShippingAddressActivity.this,ShippingPageActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(ShippingAddressActivity.this,ShippingPageActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}
	@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
	public String uploadFile(String imgPath){
		String image = null;
		InputStream is = null;
		String result = "";
		JSONObject jObject = null;
		try{
		HttpPost httpost = new HttpPost("http://128.199.213.122/services/api.class.php");
		HttpClient httpclient = new DefaultHttpClient();
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("method", new StringBody("uploadImages"));
		entity.addPart("ac", new StringBody(user.ac_token));
		entity.addPart("file", new FileBody(new File(imgPath)));
		httpost.setEntity(entity);
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity ent = response.getEntity();
		is = ent.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				is, "iso-8859-1"), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		int count =0;
		while (((line = reader.readLine()) != null) && count <20) {
			sb.append(line + "\n");
			count++;
		}
		is.close();
		result = sb.toString();
		jObject = new JSONObject(result);
		image = jObject.getString("filename");
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return image;
	}
	public class UploadImage extends AsyncTask<String, Void, List<String>> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		Dialog dialog;
		public UploadImage(Dialog dialog){
			this.dialog = dialog;
		}
    	@Override
    	protected void onPreExecute() {
    		mProgressHUD = ProgressHUD.show(ShippingAddressActivity.this,"Loading ...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected List<String> doInBackground(String... params) {
			// TODO Auto-generated method stub			
			for(int i=0;i<imgList.size();i++){
				String filename = uploadFile(imgList.get(i).path);
				filename+="=*="+imgList.get(i).amt;
				fileList.add(filename);
			}
			return fileList;
		}
		
		@Override
		protected void onPostExecute(List<String> result) {
			super.onPostExecute(result);
			if (result != null) {
				running = false;
				dialog.dismiss(); 
				if(fileList!=null && fileList.size()==imgList.size()){
					mProgressHUD.dismiss();
					CommonDto commonDto = JSONParserForGetList.getInstance().SubmitOrder(user,fileList);
		    		if(commonDto.isFlag()){
		    			List<TempImage> tmpList = Entity.query(TempImage.class).executeMulti();
			    		if(tmpList!=null && tmpList.size()>0){
			    			for(TempImage t : tmpList){
			    				t.delete();
			    			}
			    		}
			    		AlertDialog.Builder builder1 = new AlertDialog.Builder(ShippingAddressActivity.this);
			            builder1.setMessage("Submit complete.");
			            builder1.setCancelable(true);
			            builder1.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int id) {
			                    dialog.cancel();
			                    UserDto userDto = JSONParserForGetList.getInstance().getUserStatus(user.ac_token);
			                    UserVO user = Entity.query(UserVO.class).where("id").eq(1).execute();
			                    user = user.convertDtoToVo(userDto);
			                    user.id = 1;
								user.save();
			                    Intent i = new Intent(ShippingAddressActivity.this,MainPhotoSelectActivity.class);
								startActivity(i);
								overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
								finish();
			                }
			            });
			            AlertDialog alert11 = builder1.create();
			            alert11.show();
						
		    		}
				}
			}
			
		}
		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}


	}

}
