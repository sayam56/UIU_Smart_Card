package com.example.smartid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageButton;

public class ProfileActivity extends AppCompatActivity {
    Button profileLogout,profileBack;
    String navNameVal, navRoleval, navRFID,extraID;
    TextView profileName, profileRole, profileRfid,profileID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileLogout = findViewById(R.id.profileLogout);
        profileBack = findViewById(R.id.profileBack);
        profileName = findViewById(R.id.profileName);
        profileRole = findViewById(R.id.profileRole);
        profileRfid = findViewById(R.id.profileRFID);
        profileID = findViewById(R.id.profileID);



        navNameVal = getIntent().getStringExtra("name");
        navRoleval = getIntent().getStringExtra("role");
        navRFID = getIntent().getStringExtra("rfid");
        extraID = getIntent().getStringExtra("id");

        profileName.setText(navNameVal);
        profileRole.setText(navRoleval);
        profileRfid.setText(navRFID);
        profileID.setText("ID: "+extraID);


        profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,GifSplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back= new Intent(ProfileActivity.this,DashboardActivity.class);

                back.putExtra("name",navNameVal);
                back.putExtra("role",navRoleval);
                back.putExtra("rfid",navRFID);
                back.putExtra("id",extraID);

                startActivity(back);
            }
        });


    }


}
