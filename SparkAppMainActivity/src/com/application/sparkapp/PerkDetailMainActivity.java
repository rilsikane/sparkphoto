package com.application.sparkapp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sparkapp.dto.PerksDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.pkmmte.circularimageview.CircularImageView;
import com.roscopeco.ormdroid.Entity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class PerkDetailMainActivity extends Activity {
	private Utils utils;
	private ImageView perksImage;
	private TextView perksName,dueDate,perk_detail;
	private RelativeLayout reedem;
	private CircularImageView  sponserImage;
	private InputMethodManager imm;
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
		
		 sponserImage = (CircularImageView ) findViewById(R.id.imageView3);
		
		 perksImage = (ImageView) findViewById(R.id.imageView2);
		 perksName = (TextView) findViewById(R.id.perkName);
		 Typeface face = Typeface.createFromAsset(getAssets(),"fonts/ThaiSansNeue-Bold.ttf");
		 perksName.setTypeface(face);
		 dueDate = (TextView) findViewById(R.id.duedate);
		 perk_detail = (TextView) findViewById(R.id.perk_detail);
		 reedem = (RelativeLayout) findViewById(R.id.reedem);
		 imm = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);
		
		
		//final PerksDto perksDto = getIntent().getExtras().getParcelable("perksDto");
		 new InitAndLoadData().execute();
	
	}
	public class InitAndLoadData extends AsyncTask<String, Void, PerksDto>
	implements OnCancelListener {
		ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(PerkDetailMainActivity.this,"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected PerksDto doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			return getIntent().getExtras().getParcelable("perksDto");
		}

		@Override
		protected void onPostExecute(final PerksDto perksDto) {
			super.onPostExecute(perksDto);
				if(perksDto!=null){
					Picasso.with(getApplicationContext()).load(perksDto.getCoverImages()).into(perksImage);
					perksName.setText(perksDto.getShortDescription());
					dueDate.setText(perksDto.getTimeExpire());
					perk_detail.setText(perksDto.getDescription());
					if(perksDto.getUsed()){
						//reedem.setBackgroundColor(Color.BLACK);
						reedem.setVisibility(View.GONE);
//						Picasso.with(getApplicationContext()).load(R.drawable.redeem).into(reedem);
						
					}
					
					URL url_value = null;
					new InitAndLoadData2(url_value,perksDto).execute();
					//sponserImage.setImageBitmap(mIcon1);
					
					
					ImageView backIcon = (ImageView) findViewById(R.id.imageView1);
					backIcon.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent i = new Intent(PerkDetailMainActivity.this,PerkPageActivity.class);
							i.putExtra("type",perksDto.getType());
							startActivity(i);
							overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
							finish();
						}
					});
					
					reedem.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							new AlertDialog.Builder(PerkDetailMainActivity.this)
						    .setTitle("Redeem Confirmation")
						    .setMessage("Are you sure you would like to redeem this Perk now?")
						    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) {
						        	if(!Utils.isNotEmpty(perksDto.getLink()) && Utils.isNotEmpty(perksDto.getCode())){						        		
						        		final EditText input = new EditText(PerkDetailMainActivity.this);
										LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						                        LinearLayout.LayoutParams.MATCH_PARENT,
						                        LinearLayout.LayoutParams.MATCH_PARENT);
										input.setLayoutParams(lp);
										input.requestFocus();
										imm.showSoftInput(input, 0);
										AlertDialog.Builder builder1 = new AlertDialog.Builder(
												PerkDetailMainActivity.this);
										builder1.setMessage("Put your reedeem code");
										builder1.setCancelable(true);
										builder1.setPositiveButton("Ok",
												new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog,
															int id) {
													String acCode = Entity.query(UserVO.class).where("id=1").execute().ac_token;
													final UserDto dto = JSONParserForGetList.getInstance().ReedeemCode(input.getText().toString(), perksDto.getId(), acCode);
														if(dto!=null){
															AlertDialog.Builder builder1 = new AlertDialog.Builder(
																	PerkDetailMainActivity.this);
															builder1.setMessage("Success! Please enjoy your perk!");
															builder1.setCancelable(true);
															builder1.setPositiveButton("Ok",
																	new DialogInterface.OnClickListener() {
																		public void onClick(DialogInterface dialog,
																				int id) {
																			UserVO user = Entity.query(UserVO.class).where("id=1").execute();
																			user = user.convertDtoToVo(dto);
																			 user.id = 1;
																			 user.save();
																			 reedem.setVisibility(View.GONE);
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
										dialog.dismiss();
						        	}else if(!Utils.isNotEmpty(perksDto.getLink()) && !Utils.isNotEmpty(perksDto.getCode()))
						        	{
						        		String acCode = Entity.query(UserVO.class).where("id=1").execute().ac_token;
						        		final UserDto dto = JSONParserForGetList.getInstance().ReedeemCode("", perksDto.getId(), acCode);
						        		if(dto!=null){
											AlertDialog.Builder builder1 = new AlertDialog.Builder(
													PerkDetailMainActivity.this);
											builder1.setMessage("Successful redemption. Please enjoy your Perk!");
											builder1.setCancelable(true);
											builder1.setPositiveButton("Ok",
													new DialogInterface.OnClickListener() {
														public void onClick(DialogInterface dialog,
																int id) {
															UserVO user = Entity.query(UserVO.class).where("id=1").execute();
															user = user.convertDtoToVo(dto);
															 user.id = 1;
															 user.save();
															 reedem.setVisibility(View.GONE);
															dialog.cancel();
														}
													});
											AlertDialog alert11 = builder1.create();
											alert11.show();
										}
						        	}else{
						        		Intent i = new Intent(Intent.ACTION_VIEW);
						        		i.setData(Uri.parse(perksDto.getLink()));
						        		startActivity(i);
						        		dialog.dismiss();
						        	}
						    		
						        }
						     })
						    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						            // do nothing
						        	dialog.dismiss();
						        }
						     })
						    .setIcon(android.R.drawable.ic_dialog_alert)
						     .show();
						}
					});
				}
			mProgressHUD.dismiss();
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}

	}
	public class InitAndLoadData2 extends AsyncTask<String, Void, Bitmap> implements OnCancelListener {
		ProgressHUD mProgressHUD;
		private URL url_value;
		private PerksDto perksDto;
		public InitAndLoadData2(URL url,PerksDto perk){
			this.url_value = url;
			this.perksDto = perk;
		}
		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(PerkDetailMainActivity.this,"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				url_value = new URL(perksDto.getLogo_image());
				return BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute( Bitmap bitMap) {
			super.onPostExecute(bitMap);
				if(bitMap!=null){
					sponserImage.setImageBitmap(bitMap);
					mProgressHUD.dismiss();
				}else{
					mProgressHUD.dismiss();
				}
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}

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
