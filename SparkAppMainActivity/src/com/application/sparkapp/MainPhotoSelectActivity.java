package com.application.sparkapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.annotate.JsonIgnore;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.sparkapp.dto.CommonDto;
import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.UserVO;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
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
public class MainPhotoSelectActivity extends Activity {
	
	AlertDialog.Builder builder;
	AlertDialog alertDialog;
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
    final static private String APP_KEY = "1534822403398306";
    final static private String APP_SECRET = "326ad414b4480029738fb29181d4e7f4";
    final static private AccessType ACCESS_TYPE = AccessType.AUTO;
    private DropboxAPI<AndroidAuthSession> mDBApi;
    private boolean doubleBackToExitPressedOnce;
    private ProgressHUD mProgressHUD;
    private RadioButton radioButton;
    private UserDto userDto;
    private UserVO user;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main_photo_select);
		System.gc();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		ImageView cameraIcon = (ImageView) findViewById(R.id.imageView2);
		ImageView selectIcon = (ImageView) findViewById(R.id.imageView3);
		ImageView settingIcon = (ImageView) findViewById(R.id.imageView1);
		ImageView activityNoti = (ImageView) findViewById(R.id.activityNoti);
		ImageView perkIcon = (ImageView) findViewById(R.id.imageView4);
		radioButton = (RadioButton) findViewById(R.id.radioButton1);
		
		user = Entity.query(UserVO.class).where("id").eq("1").execute();
		if(user!=null){
			userDto = JSONParserForGetList.getInstance().getUserStatus(user.ac_token);
			if(userDto!=null){
				
				nextTimeCanUpload = "now".equals(userDto.getNextTimeCanUpload());
				if("D".equals(user.tutorial)){
					radioButton.setChecked(false);
				}else if("".equals(user.tutorial)){
					user.tutorial = "I";
					user.save();
					radioButton.setChecked(true);
				}else{
					user.tutorial = "A";
					user.save();
					radioButton.setChecked(true);
				}
				user = user.convertDtoToVo(userDto);
				user.save();
			}
		}
		
		perkIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainPhotoSelectActivity.this,PerkPageActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
		activityNoti.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainPhotoSelectActivity.this,ActivityNotificationActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
		settingIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainPhotoSelectActivity.this,SettingPageActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
		radioButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if((user.tutorial == null|| "".equals(user.tutorial))){
					radioButton.setChecked(true);
					user.tutorial = "I";
					user.save();
				}else if("D".equals(user.tutorial)){
					radioButton.setChecked(true);
					 user.tutorial = "A";
					 user.save();
				}else{
					 radioButton.setChecked(false);
					 user.tutorial = "D";
					 user.save();
				}
			}
		});
		
		session = Session.getActiveSession();					
		cameraIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(user!=null){
					userDto = JSONParserForGetList.getInstance().getUserStatus(user.ac_token);
					if(userDto!=null){
						nextTimeCanUpload = "now".equals(userDto.getNextTimeCanUpload());
						user = user.convertDtoToVo(userDto);
						user.save();
					}
				}
				if(nextTimeCanUpload){
					captureImage();
				}else{
					showPerkDialog();
				}			
			}
		});
		selectIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(MainPhotoSelectActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.custom_select_img);	
				RelativeLayout closeDialog = (RelativeLayout) dialog.findViewById(R.id.close_dialog_layout);
				ImageView photoFromSD = (ImageView) dialog.findViewById(R.id.imageView1);
//				ImageView facebookBtn = (ImageView) dialog.findViewById(R.id.imageView2);
//				ImageView dropBoxBtn = (ImageView) dialog.findViewById(R.id.imageView3);
				
				if(user!=null){
					userDto = JSONParserForGetList.getInstance().getUserStatus(user.ac_token);
					
					if(userDto!=null){
						nextTimeCanUpload = "now".equals(userDto.getNextTimeCanUpload());
						user = user.convertDtoToVo(userDto);
						user.save();
					}
				}
				
				closeDialog.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
//				dropBoxBtn.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//							AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
//							AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
//							mDBApi = new DropboxAPI<AndroidAuthSession>(session);
//							mDBApi.getSession().startOAuth2Authentication(MainPhotoSelectActivity.this);					
//					}
//				});
				photoFromSD.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						if(nextTimeCanUpload){
						Intent i = new Intent(MainPhotoSelectActivity.this,ImageListActivity.class);
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
//				facebookBtn.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						dialog.dismiss();
//						if(nextTimeCanUpload){
//							session = Session.getActiveSession();
//							if (session!=null) {
//	                        	 mProgressHUD= ProgressHUD.show(MainPhotoSelectActivity.this,"Loading ...", true,true,new OnCancelListener() {
//										
//										@Override
//										public void onCancel(DialogInterface dialog) {
//											// TODO Auto-generated method stub
//											mProgressHUD.dismiss();
//										}
//	                        	 });
//								Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
//
//			                         @Override
//			                         public void onCompleted(GraphUser user, Response response) {
//
//			                        	  if (user != null) {
//			                        		  if (hasPhotoPermissions()){
//			                        		    mProgressHUD.dismiss();
//						                        session.getAccessToken();				                        
//						                        user.getFirstName();
//						                        user.getId();
//						                        user.getName();
//						                        //Facebook API:https://developers.facebook.com/tools/explorer/
//						                        Intent i = new Intent(MainPhotoSelectActivity.this, ImageListActivity.class);
//						                        i.putExtra("LOAD_STATE", IMG_FROM_FACEBOOK);
//						                        i.putExtra("facebookUserId", user.getId());
//						                        i.putExtra("loadImageState", 0);
//			                                    startActivity(i);
//			                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//			                                    finish();
//			                        		  }else{
//			                        			  session.requestNewPublishPermissions(new Session.NewPermissionsRequest(MainPhotoSelectActivity.this, "user_photos"));
//			                        		  }
//						                    }
//			                             
//			                         }   
//			                     }); 
//			                     Request.executeBatchAsync(request);
//					        }else{
//					        	
//				                    // Ask for username and password
//				                    OpenRequest op = new Session.OpenRequest(MainPhotoSelectActivity.this);
//	
//				                    op.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
//				                    op.setCallback(null);
//	
//				                    List<String> permissions = new ArrayList<String>();
//				                    permissions.add("publish_stream");
//				                    permissions.add("user_likes");
//				                    permissions.add("email");
//				                    permissions.add("user_birthday");
//				                    permissions.add("user_photos");
//				                    op.setPermissions(permissions);
//	
//				                    Session session = new Builder(MainPhotoSelectActivity.this).build();
//				                    Session.setActiveSession(session);
//				                    session.openForPublish(op);
//				                }
//					        
//						}else{
//							showPerkDialog();
//						}
//					}
//				});
				
				
			}
		});
	
	}

	private void captureImage() {
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
	public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
	public void showPerkDialog(){
		final Dialog perkDialog = new Dialog(MainPhotoSelectActivity.this);
		perkDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		perkDialog.setContentView(R.layout.custom);	
		RelativeLayout closePerkDialog = (RelativeLayout) perkDialog.findViewById(R.id.close_dialog_layout);
		TextView day = (TextView) perkDialog.findViewById(R.id.textView7);
		TextView hour = (TextView) perkDialog.findViewById(R.id.textView2);
		TextView min = (TextView) perkDialog.findViewById(R.id.textView4);
		RelativeLayout goToPerk = (RelativeLayout) perkDialog.findViewById(R.id.gotoPerk);
		UserDto dto = JSONParserForGetList.getInstance().getUserStatus(user.ac_token);
		if(dto.getNextTimeCanUpload()!=null){
			Long sec = Long.parseLong(Utils.isNumeric(dto.getNextTimeCanUpload())?dto.getNextTimeCanUpload():"0");
			 int days = (int)TimeUnit.SECONDS.toDays(sec);        
			 long hours = TimeUnit.SECONDS.toHours(sec) - (days *24);
			 long minute = TimeUnit.SECONDS.toMinutes(sec) - (TimeUnit.SECONDS.toHours(sec)* 60);
			 //long second = TimeUnit.SECONDS.toSeconds(sec) - (TimeUnit.SECONDS.toMinutes(sec) *60);
			 
			 day.setText(days+"");
			 hour.setText(hours+"");
			 min.setText(minute+"");
		}
		goToPerk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainPhotoSelectActivity.this,PerkPageActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		});
		closePerkDialog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				perkDialog.dismiss();
			}
		});
		perkDialog.show();
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
	public void onBackPressed(){
		if (doubleBackToExitPressedOnce) {
	        super.onBackPressed();
	        return;
	    }

	    this.doubleBackToExitPressedOnce = true;
	    Toast.makeText(this, "Please press BACK again to exit", Toast.LENGTH_SHORT).show();

	    new Handler().postDelayed(new Runnable() {

	        @Override
	        public void run() {
	            doubleBackToExitPressedOnce=false;                       
	        }
	    }, 2000);
	}

	@Override
	protected void onResume() {
	    super.onResume();

	    if (mDBApi!=null) {
	    	 if (mDBApi.getSession().authenticationSuccessful()) {
	    	        try {
	    	            // Required to complete auth, sets the access token on the session
	    	            mDBApi.getSession().finishAuthentication();
	    	            
	    	            String accessToken = mDBApi.getSession().getOAuth2AccessToken();
	    	            
	    	            Entry contact = mDBApi.metadata("/", 0, null, true, null);
	    	            List<Entry> CFolder = contact.contents;
	    	            for (Entry entry : CFolder) {
	    	            	
	    	            	Log.i("DbExampleLog", "Folder: " + entry.fileName());
	    	            	
	    	            }
	    	            
	    	            
	    	        } catch (IllegalStateException e) {
	    	            Log.i("DbAuthLog", "Error authenticating", e);
	    	        } catch (DropboxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    }

	    }
	}
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            	//imgPath
            	Intent i = new Intent(MainPhotoSelectActivity.this,ImageCropActivity.class);
            	i.putExtra("imgPath", filepath);
            	i.putExtra("ActivtyPageFrom","MainPhotoSelect");
            	startActivity(i);
            	finish();
            	overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            	
            } else if (resultCode == RESULT_CANCELED) {} else {}
        }else{
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
                                        
                                        Intent i = new Intent(MainPhotoSelectActivity.this, ImageListActivity.class);
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

        
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
    private boolean hasPhotoPermissions() {
        return session.getPermissions().contains("user_photos");
    }

	public class CaptureImage extends AsyncTask<String, Void, Uri>
			implements OnCancelListener {
		ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(MainPhotoSelectActivity.this,
					"Loading ...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected Uri doInBackground(String... params) {
			// TODO Auto-generated method stub
			 fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
			return fileUri;
		}

		@Override
		protected void onPostExecute(Uri result) {
			super.onPostExecute(result);
			if (result != null) {
				
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
}
