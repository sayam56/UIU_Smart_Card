package com.example.smartid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class AboutUs extends AppCompatActivity {
    ImageButton aboutBtn1,aboutBtn2,aboutBtn3;
    Button aboutBack;
    String navNameVal, navRoleval, navRFID,extraID, dept, batch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        aboutBtn1 = findViewById(R.id.aboutBtn1);
        aboutBack = findViewById(R.id.aboutBack);

        navNameVal = getIntent().getStringExtra("name");
        navRoleval = getIntent().getStringExtra("role");
        navRFID = getIntent().getStringExtra("rfid");
        extraID = getIntent().getStringExtra("id");
        dept = getIntent().getStringExtra("dept");
        batch = getIntent().getStringExtra("batch");


        aboutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back= new Intent(AboutUs.this,DashboardActivity.class);

                back.putExtra("name",navNameVal);
                back.putExtra("role",navRoleval);
                back.putExtra("rfid",navRFID);
                back.putExtra("id",extraID);
                back.putExtra("dept",dept);
                back.putExtra("batch",batch);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);

                AboutUs.this.finish();
            }
        });

        aboutBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sayam = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/aisayam"));
                sayam.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sayam);

            }
        });


    }
}
