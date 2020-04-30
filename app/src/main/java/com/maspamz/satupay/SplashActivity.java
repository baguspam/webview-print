package com.maspamz.satupay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import java.util.Timer;

/**
 * Created by Maspamz on 30/09/2018.
 *
 */

public class SplashActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this,
                        MainActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);

    }
}
