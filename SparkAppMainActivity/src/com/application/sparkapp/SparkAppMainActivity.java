package com.application.sparkapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.application.sparkapp.dto.UserDto;
import com.application.sparkapp.json.JSONParserForGetList;
import com.application.sparkapp.model.TempImage;
import com.application.sparkapp.model.UserVO;
import com.application.sparkapp.util.DateUtil;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.ORMDroidApplication;

@SuppressWarnings("deprecation")

public class SparkAppMainActivity extends Activity {
	private static String PAGE_FROM = "facebookLogin";
    private Utils utils;
    private  Session currentSession;
    String FILENAME = "AndroidSSO_data";

    
	@Override
	@SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault("fonts/ThaiSansNeue-Regular.ttf", R.attr.fontPath);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spark_app_main);
        ORMDroidApplication.initialize(SparkAppMainActivity.this);
        
        System.gc();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
        
       UserVO user = Entity.query(UserVO.class).where("id").eq(1).execute();

       if (user != null && "A".equals(user.status)) {
           UserDto result = JSONParserForGetList.getInstance().getUserStatus(user.ac_token);
           List<TempImage> tempList = Entity.query(TempImage.class).executeMulti();
           if (tempList != null) {
               for (TempImage temp : tempList) {
                   temp.delete();
               }
           }
           if (result != null) {
               if (("D".equals(user.tutorial)) || "I".equals(user.tutorial)) {
                   user = user.convertDtoToVo(result);
                   user.id = 1;
                   user.status = "A";
                   user.save();
                   Intent i = new Intent(SparkAppMainActivity.this, TutorialPageOneActivity.class);
                   startActivity(i);
                   finish();
                   overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
               } else {
                   user = user.convertDtoToVo(result);
                   user.id = 1;
                   user.status = "A";
                   user.save();
                   Intent i = new Intent(SparkAppMainActivity.this, MainPhotoSelectActivity.class);
                   startActivity(i);
                   finish();
                   overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
               }
           }

       }


        utils = new Utils(getApplicationContext(), this);
        int screenWidth = utils.getScreenWidth();
        int screenHeight = utils.getScreenHeight();



        utils = new Utils(getApplicationContext(), this);
        RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
        BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.spark_welcome, screenWidth, screenHeight));
        root_id.setBackgroundDrawable(ob);
        
        ImageView loginWithFacebook = (ImageView) findViewById(R.id.imageView3);
        ImageView emailLogin = (ImageView) findViewById(R.id.imageView4);
        ImageView logo = (ImageView) findViewById(R.id.imageView1);
        logo.getLayoutParams().height = (30 * screenHeight) / 100;
        logo.getLayoutParams().width = (30 * screenHeight) / 100;

        ImageView logo_text = (ImageView) findViewById(R.id.imageView2);
        logo_text.getLayoutParams().height = (20 * screenHeight) / 100;
        logo_text.getLayoutParams().width = (75 * screenWidth) / 100;

        RelativeLayout layoutTop = (RelativeLayout) findViewById(R.id.layoutTop);
        layoutTop.getLayoutParams().height = (15 * screenHeight) / 100;
        RelativeLayout layoutBottom = (RelativeLayout) findViewById(R.id.layoutBottom);
        layoutBottom.getLayoutParams().height = (15 * screenHeight) / 100;

        loginWithFacebook.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                currentSession = Session.getActiveSession();
                if (currentSession == null || currentSession.getState().isClosed()) {
                    Session session = new Session.Builder(getApplicationContext()).build();
                    Session.setActiveSession(session);
                    currentSession = session;
                }

                if (currentSession.isOpened()) {
                	 Request request = Request.newMeRequest(currentSession, new Request.GraphUserCallback() {
                         @Override
                         public void onCompleted(GraphUser user, Response response) {
                        		new Request(
                             			 Session.getActiveSession(),
                             		    "/me/permissions",
                             		    null,
                             		    HttpMethod.DELETE,
                             		    new Request.Callback() {
                             		        public void onCompleted(Response response) {
                             		        	
                             		        }
                             		    }
                             		).executeAndWait();
                                faceBookLogin(user, currentSession);
                             
                         }   
                     }); 
                     Request.executeBatchAsync(request);
                   

                } else if (!currentSession.isOpened()) {
                	
                    OpenRequest op = new Session.OpenRequest(SparkAppMainActivity.this);

                    op.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
                    op.setCallback(null);

                    List<String> permissions = new ArrayList<String>();
                    permissions.add("publish_stream");
                    permissions.add("user_likes");
                    permissions.add("email");
                    permissions.add("user_birthday");
                    permissions.add("user_photos");
                    op.setPermissions(permissions);

                    Session session = new Builder(SparkAppMainActivity.this).build();
                    Session.setActiveSession(session);
                    session.openForPublish(op);
                }
            }
        });
        
        emailLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SparkAppMainActivity.this, EmailLoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
			}
		});
        layoutBottom.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(SparkAppMainActivity.this, SignUpPageOneMainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
    public void call(Session session, SessionState state, Exception exception) {
    	System.out.println("TESTTTTT");
    }
    
    

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Session.getActiveSession() != null) {
        
            Session.getActiveSession().onActivityResult(this, requestCode,resultCode, data);
        }

        Session currentSession = Session.getActiveSession();
        if (currentSession == null || currentSession.getState().isClosed()) {
            Session session = new Session.Builder(getApplicationContext()).build();
            Session.setActiveSession(session);
            currentSession = session;
        }
        

        if (currentSession.isOpened()) {
            Session.openActiveSession(this, true, new Session.StatusCallback() {

                @Override
                public void call(final Session session, SessionState state, Exception exception) {

                    if (session.isOpened()) {

                        Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                            @Override
                            public void onCompleted(GraphUser user, Response response) {
                            	
                                faceBookLogin(user, session);
                            }
                        });
                    }
                }
            });
        }
    } 
    public void faceBookLogin(GraphUser user,Session session){
    	if (user != null) {
    		try{
				UserDto dto = new UserDto();
				dto.setFb_access_token(session.getAccessToken());
				dto.setEmail(user.getProperty("email").toString());
				dto.setFirstname(user.getFirstName());
				dto.setLastname(user.getLastName());
				String gender = user.getProperty("gender").toString();
				dto.setGender("male".equals(gender) ? "0" : "1");
				if(user.getBirthday()!=null){
					Date dob = DateUtil.convertStringToDateByFormat(user.getBirthday(), DateUtil.FACEBOOK_DATE_PATTERN);
					dto.setBirthday(DateUtil.toStringThaiDateDefaultFormat(dob));					
				}
				new InitAndLoadData(dto,session).execute();
    		}catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
    
    
    public class InitAndLoadData extends AsyncTask<String, Void, UserDto> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		private UserDto dto;
		private Session session;
		public InitAndLoadData(UserDto _dto,Session _session){
			this.dto = _dto;
			this.session = _session;
		}
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(SparkAppMainActivity.this,"Loading ...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected UserDto doInBackground(String... params) {
			// TODO Auto-generated method stub						
			return JSONParserForGetList.getInstance().Login(dto);
		}
		
		@Override
		protected void onPostExecute(UserDto result) {
			super.onPostExecute(result);
			if (result == null) {
				Intent i = new Intent(SparkAppMainActivity.this, SignUpPageOneMainActivity.class);
				i.putExtra("userDto", (Parcelable) dto);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
				mProgressHUD.dismiss();
			} else {
				UserVO userVO = Entity.query(UserVO.class).where("id").eq(1).execute();
				  if(userVO==null){
					  userVO = new UserVO();
					  userVO = userVO.convertDtoToVo(result);
					  userVO.id = 1;
					  userVO.status = "A";
					  userVO.tutorial = "";
					  userVO.save();
	                Session.setActiveSession(session);
					  Intent i = new Intent(SparkAppMainActivity.this, TutorialPageOneActivity.class);
					  i.putExtra("INTENT_FROM", PAGE_FROM);	  
	               startActivity(i);
	               overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	               finish();
					mProgressHUD.dismiss();
				  }
				  else{
					  if(("D".equals(userVO.tutorial)) || "I".equals(userVO.tutorial)){
					  userVO = userVO.convertDtoToVo(result);
					  userVO.id = 1;
					  userVO.status = "A";
					  userVO.save();
					  Session.setActiveSession(session);
					  Intent i = new Intent(SparkAppMainActivity.this,TutorialPageOneActivity.class);
					  startActivity(i);
					  finish();
					  overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					  mProgressHUD.dismiss();
					  }else{
						  userVO = userVO.convertDtoToVo(result);
						  userVO.id = 1;
						  userVO.status = "A";
						  userVO.save();
						  Session.setActiveSession(session);
						  Intent i = new Intent(SparkAppMainActivity.this,MainPhotoSelectActivity.class);
						  startActivity(i);
						  finish();
						  overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						  mProgressHUD.dismiss();
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
