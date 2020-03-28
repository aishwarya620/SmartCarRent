package com.example.cc14.smartcarrent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView mImgHead;
    private TextView mTxtHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        bindViews();
    }
    private void bindViews() {

        mImgHead = (ImageView) findViewById(R.id.imgHead);
        mTxtHead = (TextView) findViewById(R.id.txtHead);
        new Timer().schedule(new TimerTask(){
            public void run() {

                Preferences preferences = new Preferences(SplashScreenActivity.this);

                if (preferences.get_is_loggedin().equals("1")) {
                    if (preferences.getUserType().equals("User")) {
                        startActivity(new Intent(SplashScreenActivity.this, UserDashboard.class));
                        finish();
                    }else {
                        startActivity(new Intent(SplashScreenActivity.this, OwnerDashboard.class));
                        finish();
                    }
                }else {
                    startActivity(new Intent(SplashScreenActivity.this, IndexActivity.class));
                    finish();

                }
            }
        }, 2000);
    }

}
