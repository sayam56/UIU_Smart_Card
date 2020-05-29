package com.example.smartid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
//this is the splash page
public class MainActivity extends AppCompatActivity {
 private static  int SPLASH_TIME_OUT=2000;
 Animation from_upper,from_lower;
 Button btn;
 ImageView ivUpper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
       setContentView(R.layout.activity_main);

        ivUpper = (ImageView) findViewById(R.id.upper_anim);
        btn =  (Button) findViewById(R.id.lower_anim);

        from_upper = AnimationUtils.loadAnimation(this,R.anim.from_upper);
        from_lower = AnimationUtils.loadAnimation(this,R.anim.from_lower);

        ivUpper.setAnimation(from_upper);
        btn.setAnimation(from_lower);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent home=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(home);
                finish();

            }
        },SPLASH_TIME_OUT);



    }
}
