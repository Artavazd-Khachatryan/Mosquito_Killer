package com.bastkiller.sound.mosquitosoundkiller;

import android.content.Intent;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //progress_bar = findViewById(R.id.progress_bar);


        final int SPLASH_DISPLAY_LENGTH = 1500;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getMainActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    private void getMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
