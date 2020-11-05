package com.example.smartid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import pl.droidsonroids.gif.GifImageButton;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvNavName,tvNavRole,tvNavRFID;
    NavigationView navigation;
    DrawerLayout drawer;
    View headerView;
    String navNameVal, navRoleval, navRFID,extraID,dept,batch;
    GifImageButton attend, pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigation = (NavigationView) findViewById(R.id.navigation);
        headerView = navigation.getHeaderView(0);
        navigation.setNavigationItemSelectedListener(this);
        tvNavName = (TextView) headerView.findViewById(R.id.tvNavName);
        tvNavRole = (TextView) headerView.findViewById(R.id.tvNavRole);
        tvNavRFID = (TextView) headerView.findViewById(R.id.tvNavRFID);

        attend = findViewById(R.id.attendGif);
        pay = findViewById(R.id.payGif);


        navNameVal = getIntent().getStringExtra("name");
        navRoleval = getIntent().getStringExtra("role");
        navRFID = getIntent().getStringExtra("rfid");
        extraID = getIntent().getStringExtra("id");
        dept = getIntent().getStringExtra("dept");
        batch = getIntent().getStringExtra("batch");

        tvNavName.setText(navNameVal);
        tvNavRole.setText(navRoleval);
        tvNavRFID.setText(navRFID);


        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent attend = new Intent(DashboardActivity.this, AttendanceActivity.class);
                attend.putExtra("name",navNameVal);
                attend.putExtra("role",navRoleval);
                attend.putExtra("id",extraID);
                attend.putExtra("rfid",navRFID);
                attend.putExtra("dept",dept);
                attend.putExtra("batch",batch);
                attend.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(attend);

            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  pay = new Intent(DashboardActivity.this, PaymentActivity.class);
                pay.putExtra("name",navNameVal);
                pay.putExtra("role",navRoleval);
                pay.putExtra("id",extraID);
                pay.putExtra("rfid",navRFID);
                pay.putExtra("dept",dept);
                pay.putExtra("batch",batch);
                pay.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pay);

            }
        });


/*
        idView = findViewById(R.id.idView);
        emailView = findViewById(R.id.emailView);
        passView = findViewById(R.id.passView);



        String idVal, emailVal, passVal;
        idVal = getIntent().getStringExtra("id");
        emailVal = getIntent().getStringExtra("email");
        passVal = getIntent().getStringExtra("pass");

        idView.setText("Your Id: " + idVal);
        emailView.setText("With Email: " + emailVal);
        passView.setText(passVal);*/

        navigation.setItemIconTintList(null);


        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.profile_page:
                //pass the profile view here
                //Toast.makeText(getApplicationContext(),"Profile Page Will Be Updated Soon",Toast.LENGTH_SHORT).show();
                Intent profile = new Intent(DashboardActivity.this,ProfileActivity.class);
                profile.putExtra("name",navNameVal);
                profile.putExtra("role",navRoleval);
                profile.putExtra("id",extraID);
                profile.putExtra("rfid",navRFID);
                profile.putExtra("dept",dept);
                profile.putExtra("batch",batch);
                startActivity(profile);
                break;
            case R.id.settings:
                //pass the settings page here
                Toast.makeText(getApplicationContext(),"Settings Page Will Be Implemented Soon",Toast.LENGTH_SHORT).show();
                break;
            case R.id.aboutUs:
                //Pass the About Us page here
                //Toast.makeText(getApplicationContext(),"We Are The Android Team :p ",Toast.LENGTH_SHORT).show();
                Intent aboutUs = new Intent(DashboardActivity.this,AboutUs.class);
                aboutUs.putExtra("name",navNameVal);
                aboutUs.putExtra("role",navRoleval);
                aboutUs.putExtra("id",extraID);
                aboutUs.putExtra("rfid",navRFID);
                aboutUs.putExtra("dept",dept);
                aboutUs.putExtra("batch",batch);
                startActivity(aboutUs);
                break;
            case R.id.logout:
                Intent intent = new Intent(DashboardActivity.this,GifSplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }
}
