package com.application.sparkapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SparkAppMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spark_app_main);
        
        int screenWidth = new Utils(getApplicationContext(), SparkAppMainActivity.this).getScreenWidth();
        int screenHeight = new Utils(getApplicationContext(), SparkAppMainActivity.this).getScreenHeight();
        
        ImageView logo = (ImageView) findViewById(R.id.imageView1);
        logo.getLayoutParams().height = (30*screenHeight)/100;
        logo.getLayoutParams().width = (30*screenHeight)/100;
        
        ImageView logo_text = (ImageView) findViewById(R.id.imageView2);
        logo_text.getLayoutParams().height=(20*screenHeight)/100;
        logo_text.getLayoutParams().width=(75*screenWidth)/100;
        
        RelativeLayout layoutTop = (RelativeLayout) findViewById(R.id.layoutTop);
        layoutTop.getLayoutParams().height = (15*screenHeight)/100;
        RelativeLayout layoutBottom = (RelativeLayout) findViewById(R.id.layoutBottom);
        layoutBottom.getLayoutParams().height = (15*screenHeight)/100;
    }
}
