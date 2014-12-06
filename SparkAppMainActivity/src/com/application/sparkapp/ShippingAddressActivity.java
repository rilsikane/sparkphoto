package com.application.sparkapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
	private EditText address_block,address_street_name,address_unit_number1,address_unit_number2,address_postal;
	List<TempImage> imgList;
	int progress = 0;
	private UserVO user;
	private List<String> fileList = new ArrayList<String>();
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_shipping_address);
		System.gc();
		utils = new Utils(this, this);
		utils.setupUI(findViewById(R.id.imageGuid));
		RelativeLayout fullGuid = (RelativeLayout) findViewById(R.id.imageGuid);
		
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.address_background, utils.getScreenWidth(), utils.getScreenHeight()));
		fullGuid.setBackgroundDrawable(ob);
		goToPreviousPage = (ImageView) findViewById(R.id.imageView1);
		confirmBtn = (TextView) findViewById(R.id.textView2);			
		address_block = (EditText) findViewById(R.id.editText3);
		address_street_name = (EditText) findViewById(R.id.editText4);
		address_unit_number1 = (EditText) findViewById(R.id.editText7);
		address_unit_number2 = (EditText) findViewById(R.id.EditText01);
		address_postal = (EditText) findViewById(R.id.editText8);
        
		address_unit_number1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
		address_unit_number1.addTextChangedListener(new TextWatcher() {		
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(address_unit_number1.getText().length()==0){
					address_unit_number1.requestFocus();
				}else if(address_unit_number1.getText().length()==2){
					address_unit_number2.requestFocus();
				}else if(address_unit_number1.getText().length()>2){
					address_unit_number2.requestFocus();
				}else{
					address_unit_number1.requestFocus();
				}
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
		});
		address_unit_number2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});
		address_unit_number2.addTextChangedListener(new TextWatcher() {		
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(address_unit_number2.getText().length()==0){
					address_unit_number1.requestFocus();
				}else if(address_unit_number2.getText().length()==5){
					address_unit_number2.requestFocus();
				}else if(address_unit_number2.getText().length()==4){
					address_unit_number2.requestFocus();
				}else if(address_unit_number2.getText().length()==3){
					address_unit_number2.requestFocus();
				}else if(address_unit_number2.getText().length()==2){
					address_unit_number2.requestFocus();
				}else if(address_unit_number2.getText().length()==1){
					address_unit_number2.requestFocus();
				}else{
					address_unit_number2.requestFocus();
				}
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
		});
		
		user = Entity.query(UserVO.class).where("id").eq(1).execute();
		if(user!=null){
			address_block.setText(user.address_block);
			address_street_name.setText(user.address_street_name);
			if(user.address_unit_number!=null && user.address_unit_number.length()!=0){
				String[] unitNumber = user.address_unit_number.split("-");
				address_unit_number1.setText(Utils.isNotEmpty(unitNumber[0])?unitNumber[0]:"");
				address_unit_number2.setText(Utils.isNotEmpty(unitNumber[1])?unitNumber[1]:"");
			}
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
				dialog.show();
				new UploadImage(dialog).execute();
				
			}
		});
		goToPreviousPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ShippingAddressActivity.this,ImagePageSummaryActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(ShippingAddressActivity.this,ImagePageSummaryActivity.class);
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
			if (result != null && result.size()==result.size() && checkNotNullInList(result)) {					
					CommonDto commonDto = JSONParserForGetList.getInstance().SubmitOrder(user,result);
					dialog.dismiss(); 
					mProgressHUD.dismiss();
		    		if(commonDto.isFlag()){
		    			
			    		AlertDialog.Builder builder1 = new AlertDialog.Builder(ShippingAddressActivity.this);
			            builder1.setMessage("Success! Your prints are on the way! ");
			            builder1.setCancelable(true);
			            builder1.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int id) {
			                    dialog.cancel();
			                    List<TempImage> tmpList = Entity.query(TempImage.class).executeMulti();
					    		if(tmpList!=null && tmpList.size()>0){
					    			for(TempImage t : tmpList){
					    				t.delete();
					    			}
					    		}
					    		Utils.deleteRecursive(new File(
								Environment.getExternalStorageDirectory()
										+ "/Spark/temp_image/"));
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
						
		    		}else{
		    			AlertDialog.Builder builder1 = new AlertDialog.Builder(
								ShippingAddressActivity.this);
		    			
						String[] msgs = commonDto.getMsg().replaceAll("\\[", "")
								.replaceAll("\\]", "").split("\\.");
						if (msgs != null && msgs.length > 0) {
							String msg = "Error Please try again "
									+ System.getProperty("line.separator");
							if (msgs != null && msgs.length > 0) {
								for (String ms : msgs) {
									msg += ("-" + ms + System
											.getProperty("line.separator"));
								}

							}
							builder1.setMessage(msg);
						}
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
			}else{
    			AlertDialog.Builder builder1 = new AlertDialog.Builder(
						ShippingAddressActivity.this);

				builder1.setMessage("An error occur, Please try a again later.");
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
		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}


	}
	
	public boolean checkNotNullInList(List<String> imgList){
		boolean isNotNull = true;
		if(imgList!=null && imgList.size()>0){
			for(String s :imgList){
				if(!Utils.isNotEmpty(s)){
					isNotNull = false;
					break;
				}
			}
		}else{
			isNotNull = false;
		}
		return isNotNull;
	}

}
