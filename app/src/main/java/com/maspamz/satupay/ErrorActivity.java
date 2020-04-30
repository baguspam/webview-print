package com.maspamz.satupay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Maspamz on 30/09/2018.
 */

public class ErrorActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_page);
        //To exit
        if (getIntent().getBooleanExtra("EXIT", false)) {
            ErrorActivity.this.finish();
            System.exit(0);
        }
    }
}
