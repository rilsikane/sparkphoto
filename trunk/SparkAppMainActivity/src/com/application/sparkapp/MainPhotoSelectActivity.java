package com.application.sparkapp;

import java.util.ArrayList;
import java.util.List;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.model.GraphUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainPhotoSelectActivity extends Activity {
	
	AlertDialog.Builder builder;
	AlertDialog alertDialog;
	private Session session;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main_photo_select);
		ImageView cameraIcon = (ImageView) findViewById(R.id.imageView2);
		ImageView selectIcon = (ImageView) findViewById(R.id.imageView3);
		
		session = Session.getActiveSession();					
		cameraIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(MainPhotoSelectActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.custom);	
				RelativeLayout closeDialog = (RelativeLayout) dialog.findViewById(R.id.close_dialog_layout);
				closeDialog.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
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
				                        //Get All album
//				                        new Request(session,user.getId()+"/albums",null,HttpMethod.GET,new Request.Callback() {
//				                        	public void onCompleted(Response response) {
//				                        	        	System.out.println(response);
//				                        	        }
//				                        	    }
//				                        ).executeAsync();
//				                        //Get all image from album id
//				                        new Request(session,user.getId()+"/",null,HttpMethod.GET,new Request.Callback() {
//				                        	public void onCompleted(Response response) {
//				                        	        	System.out.println(response);
//				                        	        }
//				                        	    }
//				                        ).executeAsync();
				                        Intent i = new Intent(MainPhotoSelectActivity.this, ImageListActivity.class);
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
