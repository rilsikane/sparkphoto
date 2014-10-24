package com.application.sparkapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class MainPhotoSelectActivity extends Activity {
	
	AlertDialog.Builder builder;
	AlertDialog alertDialog;
	private Session session;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
    private static String filepath = "";
    private static final String IMAGE_DIRECTORY_NAME = "Spark Images";
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main_photo_select);
		System.gc();
		ImageView cameraIcon = (ImageView) findViewById(R.id.imageView2);
		ImageView selectIcon = (ImageView) findViewById(R.id.imageView3);
		ImageView settingIcon = (ImageView) findViewById(R.id.imageView1);
		ImageView activityNoti = (ImageView) findViewById(R.id.activityNoti);
		
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
		
		session = Session.getActiveSession();					
		cameraIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				captureImage();
//				final Dialog dialog = new Dialog(MainPhotoSelectActivity.this);
//				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				dialog.setContentView(R.layout.custom);	
//				RelativeLayout closeDialog = (RelativeLayout) dialog.findViewById(R.id.close_dialog_layout);
//				closeDialog.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						dialog.dismiss();
//					}
//				});
//				dialog.show();
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
				ImageView facebookBtn = (ImageView) dialog.findViewById(R.id.imageView2);
				closeDialog.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				photoFromSD.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(MainPhotoSelectActivity.this,ImageListActivity.class);
						i.putExtra("loadImageState", 1);
						startActivity(i);
						overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					}
				});
				facebookBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
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
				                        Intent i = new Intent(MainPhotoSelectActivity.this, ImageListActivity.class);
				                        i.putExtra("facebookUserId", user.getId());
				                        i.putExtra("loadImageState", 0);
	                                    startActivity(i);
	                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
			                    Intent i = new Intent(MainPhotoSelectActivity.this, ImageListActivity.class);
			                    startActivity(i);
			                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

			                } else if (!currentSession.isOpened()) {
			                    // Ask for username and password
			                    OpenRequest op = new Session.OpenRequest(MainPhotoSelectActivity.this);

			                    op.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
			                    op.setCallback(null);

			                    List<String> permissions = new ArrayList<String>();
			                    permissions.add("publish_stream");
			                    permissions.add("user_likes");
			                    permissions.add("email");
			                    permissions.add("user_birthday");
			                    op.setPermissions(permissions);

			                    Session session = new Builder(MainPhotoSelectActivity.this).build();
			                    Session.setActiveSession(session);
			                    session.openForPublish(op);
			                }
				        }
					}
				});
				
				dialog.show();
				
			}
		});
	
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
	public void onBackPressed(){
		Intent i = new Intent(MainPhotoSelectActivity.this,SparkAppMainActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
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
            	Intent i = new Intent(MainPhotoSelectActivity.this,ImageCropActivity.class);
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
                                    Toast.makeText(getApplicationContext(), "Welcome "+user.getFirstName()+" "+user.getName(), Toast.LENGTH_SHORT).show();
                                    user.getFirstName();
                                    user.getId();
                                    user.getName();
                                    
                                    Intent i = new Intent(MainPhotoSelectActivity.this, ImageListActivity.class);
                                    i.putExtra("facebookUserId", user.getId());
                                    i.putExtra("loadImageState", 0);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}
