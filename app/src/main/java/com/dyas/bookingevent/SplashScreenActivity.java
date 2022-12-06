package com.dyas.bookingevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dyas.bookingevent.utility.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();


          Thread   mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {

                        wait(4000);

                    }
                } catch (InterruptedException ex) {
                }
                // Run next activity


                finish();
                if (SessionManager.getUserObject(getApplicationContext(), "jsonmember") == null) {
                    Intent newIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivityForResult(newIntent, 0);
                    finish();
                } else {
                    Intent newIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivityForResult(newIntent, 0);
                    finish();

                }
            }
        };
        mSplashThread.start();
    }
}