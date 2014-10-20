package com.application.sparkapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

@SuppressWarnings("deprecation")
public class SparkAppMainActivity extends Activity {

    private Utils utils;
    String FILENAME = "AndroidSSO_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spark_app_main);
        utils = new Utils(getApplicationContext(), this);
        int screenWidth = utils.getScreenWidth();
        int screenHeight = utils.getScreenHeight();

        utils = new Utils(getApplicationContext(), this);
        RelativeLayout root_id = (RelativeLayout) findViewById(R.id.root_id);
        BitmapDrawable ob = new BitmapDrawable(utils.decodeSampledBitmapFromResource(getResources(), R.drawable.spark_welcome, screenWidth, screenHeight));
        root_id.setBackgroundDrawable(ob);

        ImageView loginWithFacebook = (ImageView) findViewById(R.id.imageView3);

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
                Session currentSession = Session.getActiveSession();
                if (currentSession == null || currentSession.getState().isClosed()) {
                    Session session = new Session.Builder(getApplicationContext()).build();
                    Session.setActiveSession(session);
                    currentSession = session;
                }

                if (currentSession.isOpened()) {
                    // Do whatever u want. User has logged in
                    Intent i = new Intent(SparkAppMainActivity.this, MainPhotoSelectActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else if (!currentSession.isOpened()) {
                    // Ask for username and password
                    OpenRequest op = new Session.OpenRequest(SparkAppMainActivity.this);

                    op.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
                    op.setCallback(null);

                    List<String> permissions = new ArrayList<String>();
                    permissions.add("publish_stream");
                    permissions.add("user_likes");
                    permissions.add("email");
                    permissions.add("user_birthday");
                    op.setPermissions(permissions);

                    Session session = new Builder(SparkAppMainActivity.this).build();
                    Session.setActiveSession(session);
                    session.openForPublish(op);
                }
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

    public void call(Session session, SessionState state, Exception exception) {

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

                @Override
                public void call(final Session session, SessionState state, Exception exception) {

                    if (session.isOpened()) {

                        Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                            @Override
                            public void onCompleted(GraphUser user, Response response) {
                                if (user != null) {
                                    session.getAccessToken();
                                    user.getFirstName();
                                    user.getId();
                                    user.getName();
                                    Intent i = new Intent(SparkAppMainActivity.this, MainPhotoSelectActivity.class);
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
