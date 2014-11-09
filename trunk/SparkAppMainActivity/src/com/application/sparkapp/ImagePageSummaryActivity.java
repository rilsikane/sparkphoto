package com.application.sparkapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.sparkapp.model.Login;
import com.application.sparkapp.model.TempImage;
import com.application.sparkapp.model.UserVO;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.roscopeco.ormdroid.Entity;

@SuppressLint("NewApi")
public class ImagePageSummaryActivity extends Activity {
	private Utils utils;
	private ListView summaryList;
	private TextView goToNextPage,picCount,picTotal;
	private Activity _activity;
	private int picCt,total;
	private UserVO user;
	private int newRes;
	private Session session;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
    private static String filepath = "";
    private static final String IMAGE_DIRECTORY_NAME = "Spark Images";
    private static final String IMG_FROM_FACEBOOK = "imgFace";
    private static final String IMG_FROM_DROPBOX = "imgDrop";
    private static final String IMG_FROM_GALLERY = "imgGal";
    private boolean nextTimeCanUpload;
    final static private String APP_KEY = "6lxmgb1olxyc2jz";
    final static private String APP_SECRET = "ldlb1b0s4vtzqir";
    final static private AccessType ACCESS_TYPE = AccessType.AUTO;
    private DropboxAPI<AndroidAuthSession> mDBApi;
    List<TempImg> tempList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image_page_summary);
		System.gc();
		UserVO user = Entity.query(UserVO.class).execute();
		if(user!=null){
			nextTimeCanUpload = user.nextTimeCanUpload.equals("now");
		}
		utils = new Utils(this, this);
		RelativeLayout fullGuid = (RelativeLayout) findViewById(R.id.imageGuid);
		BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.address_background, utils.getScreenWidth(), utils.getScreenHeight()));
		fullGuid.setBackgroundDrawable(ob);
		Bitmap croppedImage = (Bitmap) getIntent().getParcelableExtra("croppedImage");
		summaryList = (ListView) findViewById(R.id.summaryList);
		summaryList.setDividerHeight(0);
		goToNextPage = (TextView) findViewById(R.id.textView2);
		_activity = ImagePageSummaryActivity.this;
		ImageView goBack = (ImageView) findViewById(R.id.imageView1);
		
		picCount = (TextView) findViewById(R.id.textView4);
		picTotal = (TextView) findViewById(R.id.totalAmountImage);
		user = Entity.query(UserVO.class).execute();
		total = Integer.parseInt(user.numberPictureCanUpload);
		picTotal.setText("/"+total);
		ImageView captureMoreImage = (ImageView) findViewById(R.id.imageView2);
		ImageView addMoreImage = (ImageView) findViewById(R.id.imageView3);
		
		addMoreImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(picCt>=total){
					new AlertDialog.Builder(ImagePageSummaryActivity.this)
				    .setMessage("You have used all your credits")
				    .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // do nothing
				        	dialog.dismiss();
				        }
				     })
				    .setIcon(android.R.drawable.ic_dialog_alert)
				    .show();
				}else{
					final Dialog dialog = new Dialog(ImagePageSummaryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.custom_select_img);	
					RelativeLayout closeDialog = (RelativeLayout) dialog.findViewById(R.id.close_dialog_layout);
					ImageView photoFromSD = (ImageView) dialog.findViewById(R.id.imageView1);
					ImageView facebookBtn = (ImageView) dialog.findViewById(R.id.imageView2);
					ImageView dropBoxBtn = (ImageView) dialog.findViewById(R.id.imageView3);
					closeDialog.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dropBoxBtn.setOnClickListener(new OnClickListener() {
						
						@SuppressWarnings("deprecation")
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
								AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
								AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
								mDBApi = new DropboxAPI<AndroidAuthSession>(session);
								mDBApi.getSession().startOAuth2Authentication(ImagePageSummaryActivity.this);					
						}
					});
					photoFromSD.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							if(nextTimeCanUpload){
							Intent i = new Intent(ImagePageSummaryActivity.this,ImageListActivity.class);
							i.putExtra("loadImageState", 1);
							i.putExtra("LOAD_STATE", IMG_FROM_GALLERY);
							startActivity(i);
							overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
							finish();
							}else{
								showPerkDialog();
							}
						}
					});
					facebookBtn.setOnClickListener(new OnClickListener() {
						
						@SuppressWarnings("deprecation")
						@Override
						public void onClick(View v) {
							if(nextTimeCanUpload){
							if (session!=null) {
	
					            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
	
					                @Override
					                public void onCompleted(GraphUser user, Response response) {
					                    if (user != null) {
					                        session.getAccessToken();				                        
					                        user.getFirstName();
					                        user.getId();
					                        user.getName();
					                        //Facebook API:https://developers.facebook.com/tools/explorer/
					                        Intent i = new Intent(ImagePageSummaryActivity.this, ImageListActivity.class);
					                        i.putExtra("LOAD_STATE", IMG_FROM_FACEBOOK);
					                        i.putExtra("facebookUserId", user.getId());
					                        i.putExtra("loadImageState", 0);
		                                    startActivity(i);
		                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		                                    finish();
					                    }
					                }
					            });
					        }else{
					        	Session currentSession = Session.getActiveSession();
				                if (currentSession == null || currentSession.getState().isClosed()) {
				                    Session session = new Session.Builder(getApplicationContext()).build();
				                    Session.setActiveSession(session);
				                    currentSession = session;
				                }
	
				                if (currentSession.isOpened()) {
				                    // Do whatever u want. User has logged in
				                    Intent i = new Intent(ImagePageSummaryActivity.this, ImageListActivity.class);
				                    startActivity(i);
				                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	
				                } else if (!currentSession.isOpened()) {
				                    // Ask for username and password
				                    OpenRequest op = new Session.OpenRequest(ImagePageSummaryActivity.this);
	
				                    op.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
				                    op.setCallback(null);
	
				                    List<String> permissions = new ArrayList<String>();
				                    permissions.add("publish_stream");
				                    permissions.add("user_likes");
				                    permissions.add("email");
				                    permissions.add("user_birthday");
				                    op.setPermissions(permissions);
	
				                    Session session = new Builder(ImagePageSummaryActivity.this).build();
				                    Session.setActiveSession(session);
				                    session.openForPublish(op);
				                }
					        }
							}else{
								showPerkDialog();
							}
						}
					});
					dialog.show();
				}
			}
		});
		captureMoreImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(picCt>=total){
					new AlertDialog.Builder(ImagePageSummaryActivity.this)
				    .setMessage("You have used all your credits")
				    .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // do nothing
				        	dialog.dismiss();
				        }
				     })
				    .setIcon(android.R.drawable.ic_dialog_alert)
				    .show();
				}else{
					captureImage();
				}
			}
		});
		goBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(ImagePageSummaryActivity.this)
			    .setTitle(getResources().getString(R.string.cancel_confirmation_header))
			    .setMessage(getResources().getString(R.string.cancel_confirmation_desc))
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	List<TempImage> tmpList = Entity.query(TempImage.class).executeMulti();
			    		if(tmpList!=null && tmpList.size()>0){
			    			for(TempImage t : tmpList){
			    				t.delete();
			    			}
			    		}
			        	Intent i = new Intent(ImagePageSummaryActivity.this,MainPhotoSelectActivity.class);
						startActivity(i);
						overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						finish();
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
		goToNextPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(picCt==total){
				Intent i = new Intent(ImagePageSummaryActivity.this,ShippingPageActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();
				}else{
					new AlertDialog.Builder(ImagePageSummaryActivity.this)
				    .setTitle("You aren’t done yet! ")
				    .setMessage("Please select more images to print!")
				    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) {				        	
				    		dialog.dismiss();
				        }
				     })
				    .setIcon(android.R.drawable.ic_dialog_alert)
				    .show();
				}
			}
		});
		Login login = Entity.query(Login.class).execute();
		tempList = new ArrayList<TempImg>();
		if(login!=null){
			List<TempImage> imgList = Entity.query(TempImage.class).where("ac_token").eq(login.ac_token).executeMulti();
			if(imgList!=null && !imgList.isEmpty()){
				
				for(TempImage tmp : imgList){
					TempImg img = new TempImg();
					img.setAmt(tmp.amt!=null ? Integer.parseInt(tmp.amt) : 0);
					img.setBgicon(tmp.originPath);
					String[] temp = tmp.path.split("\\.");
					img.setCropIcon(temp[0]+"_tmb"+"."+temp[1]);
					picCt += Integer.parseInt(tmp.amt);
					img.setTempImage(tmp);
					tempList.add(img);
					
				}
				picCount.setText(picCt+"");
			}
			
		}
		 Collections.reverse(tempList);
		LoadListAdapter adapter = new LoadListAdapter(tempList);
		summaryList.setAdapter(adapter);

		
	}
	@Override
	public void onBackPressed(){		
		new AlertDialog.Builder(this)
	    .setTitle(getResources().getString(R.string.cancel_confirmation_header))
	    .setMessage(getResources().getString(R.string.cancel_confirmation_desc))
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	    		List<TempImage> tmpList = Entity.query(TempImage.class).executeMulti();
	    		if(tmpList!=null && tmpList.size()>0){
	    			for(TempImage t : tmpList){
	    				t.delete();
	    			}
	    		}
	        	Intent i = new Intent(ImagePageSummaryActivity.this,MainPhotoSelectActivity.class);
//	    		i.putExtra("croppedImage", (Bitmap) getIntent().getParcelableExtra("croppedImage"));
	    		startActivity(i);
	    		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	    		finish();
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
	public class TempImg{
		private String bgicon,cropIcon;
		private int amt;
		private TempImage tempImage;
		
		
		public String getBgicon() {
			return bgicon;
		}
		public void setBgicon(String bgicon) {
			this.bgicon = bgicon;
		}
		public String getCropIcon() {
			return cropIcon;
		}
		public void setCropIcon(String cropIcon) {
			this.cropIcon = cropIcon;
		}
		public int getAmt() {
			return amt;
		}
		public void setAmt(int amt) {
			this.amt = amt;
		}
		public TempImage getTempImage() {
			return tempImage;
		}
		public void setTempImage(TempImage tempImage) {
			this.tempImage = tempImage;
		}
		
		
		
		
	}
	public void updatePicAmt(int id){
		TempImage tempImage = Entity.query(TempImage.class).where("id").eq(id).execute();
		tempImage.amt = newRes+"";
		tempImage.save();
	}
	public void checkAmt(){
		for(TempImg tmp : tempList){
			
		}
	}
	
	public class LoadListAdapter extends BaseAdapter{
		private ViewHolder viewHolder;
		public List<TempImg> _list;
		private int size;
		
		public LoadListAdapter(List<TempImg> list){
			this._list = list;
			utils = new Utils(_activity, _activity);
			size = (int) Math.ceil(Math.sqrt(utils.getScreenWidth() * utils.getScreenHeight()));
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return _list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return _list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView== null){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = inflater.inflate(R.layout.each_sumary_layout, null);
			View view_line = (View) convertView.findViewById(R.id.view_line);
			if(position==0){
				view_line.setVisibility(View.INVISIBLE);
			}
			viewHolder.bgImg = (ImageView) convertView.findViewById(R.id.imgBg);
			viewHolder.cropImg = (ImageView) convertView.findViewById(R.id.imageView1);
			viewHolder.amt = (TextView) convertView.findViewById(R.id.textView3);
			viewHolder.minusBt = (Button) convertView.findViewById(R.id.textView2);
			viewHolder.plusBt = (Button) convertView.findViewById(R.id.textView4);
			viewHolder.viewClick = (RelativeLayout) convertView.findViewById(R.id.summary_content);
			
			convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			newRes = 0;
			
			viewHolder.viewClick.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
		
			
			TempImg temp = _list.get(position);
			viewHolder.amt.setText(temp.getAmt()+"");
			Bitmap bgBitmap = BitmapFactory.decodeFile(temp.getBgicon());
			viewHolder.bgImg.setImageBitmap(bgBitmap);
			
			 Bitmap myBitmap = BitmapFactory.decodeFile(temp.getCropIcon());
			viewHolder.cropImg.setImageBitmap(myBitmap);
			viewHolder.minusBt.setOnClickListener(new OnButtongListener(viewHolder.amt,temp));
			viewHolder.plusBt.setOnClickListener(new OnButtongListenerPlus(viewHolder.amt,temp));
			
			return convertView;
		}

		public class ViewHolder{
			public Button minusBt,plusBt;
			public TextView amt;
			public ImageView cropImg;
			public ImageView bgImg;
			public RelativeLayout viewClick;
		}
		public class OnButtongListenerPlus implements OnClickListener{
			private TextView val;
			private TempImg temp;
			public OnButtongListenerPlus(TextView val,TempImg temp){
				this.val = val;
				this.temp = temp;
			}
			
			@Override
			public void onClick(View v) {				
			if(Integer.parseInt(picCount.getText().toString())==total){
				new AlertDialog.Builder(ImagePageSummaryActivity.this)
			    .setMessage("You have used all your credits")
			    .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	
			        	dialog.dismiss();
			        }
			     })
			    .setIcon(android.R.drawable.ic_dialog_alert)
			    .show();
			}
			newRes = temp.getAmt();
			if(newRes<total && picCt<total){
				newRes++;
				picCt++;
				val.setText(""+(newRes));
				temp.getTempImage().amt = newRes+"";
				temp.getTempImage().save();
				temp.setAmt(newRes);
			}
				picCount.setText(picCt+"");
			}
			
		}
		public class OnButtongListener implements OnClickListener{
			private TextView val;
			private TempImg temp;
			public OnButtongListener(TextView val,TempImg temp){
				this.val = val;
				this.temp = temp;
			}
			
			@Override
			public void onClick(View v) {
				if(val.getText().toString().equals("1")){
					new AlertDialog.Builder(ImagePageSummaryActivity.this)
				    .setTitle("Delete Confirmation")
				    .setMessage("Do you want to delete this item?")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) {
				        	tempList.remove(temp);
				        	LoadListAdapter adapter = new LoadListAdapter(tempList);
				    		summaryList.setAdapter(adapter);
				    		summaryList.invalidateViews();
				    		TempImage tempImage = Entity.query(TempImage.class).where("id").eq(temp.getTempImage().id).execute();
				    		tempImage.delete();
				    		dialog.dismiss();
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
				
				newRes = temp.getAmt();
				if(newRes>0&&newRes!=1){
					newRes--;
					picCt--;
					val.setText(""+(newRes));
					temp.getTempImage().amt = newRes+"";
					temp.getTempImage().save();
					temp.setAmt(newRes);
				}else{
					val.setText(""+(newRes));
				}
				
				picCount.setText(picCt+"");
			}
			
		}
		
	}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
 
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
 
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
 
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
	public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
	private static File getOutputMediaFile(int type) {
		 
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);
 
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "+ IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
 
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator+ "IMG_" + timeStamp + ".jpg");
            filepath = mediaStorageDir.getPath() + File.separator+ "IMG_" + timeStamp + ".jpg";
        } else {
            return null;
        }
 
        return mediaFile;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Session.getActiveSession() != null) {
            Session.getActiveSession().onActivityResult(this, requestCode,
                    resultCode, data);
        }

        Session currentSession = Session.getActiveSession();
        if (currentSession == null || currentSession.getState().isClosed()) {
            Session session = new Session.Builder(getApplicationContext()).build();
            Session.setActiveSession(session);
            currentSession = session;
        }
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            	//imgPath
            	Intent i = new Intent(ImagePageSummaryActivity.this,ImageCropActivity.class);
            	i.putExtra("imgPath", filepath);
            	startActivity(i);
            	finish();
            	overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            	
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        if (currentSession.isOpened()) {
            Session.openActiveSession(this, true, new Session.StatusCallback() {

                @SuppressWarnings("deprecation")
				@Override
                public void call(final Session session, SessionState state, Exception exception) {

                    if (session.isOpened()) {

                        Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                            @Override
                            public void onCompleted(GraphUser user, Response response) {
                                if (user != null) {
                                    session.getAccessToken();
                                    //Toast.makeText(getApplicationContext(), "Welcome "+user.getFirstName()+" "+user.getName(), Toast.LENGTH_SHORT).show();
                                    user.getFirstName();
                                    user.getId();
                                    user.getName();
                                    
                                    Intent i = new Intent(ImagePageSummaryActivity.this, ImageListActivity.class);
                                    i.putExtra("LOAD_STATE", IMG_FROM_FACEBOOK);
                                    i.putExtra("facebookUserId", user.getId());
                                    i.putExtra("loadImageState", 0);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    finish();
                                }
                            }
                        });
                    }
                }
            });
        }
}
    public void showPerkDialog(){
		final Dialog perkDialog = new Dialog(ImagePageSummaryActivity.this);
		perkDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		perkDialog.setContentView(R.layout.custom);	
		RelativeLayout closePerkDialog = (RelativeLayout) perkDialog.findViewById(R.id.close_dialog_layout);
		closePerkDialog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				perkDialog.dismiss();
			}
		});
		perkDialog.show();
	}
}
