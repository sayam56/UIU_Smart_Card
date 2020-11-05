package com.example.smartid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import pl.droidsonroids.gif.GifImageView;

public class GifSplashActivity extends AppCompatActivity {
    private static  int SPLASH_TIME_OUT=1700;
    GifImageView gifImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_splash);

        gifImageView = (GifImageView) findViewById(R.id.gif);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent home=new Intent(GifSplashActivity.this,HomeActivity.class);
                startActivity(home);
                finish();

            }
        },SPLASH_TIME_OUT);
    }
}
