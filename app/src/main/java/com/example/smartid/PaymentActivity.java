package com.example.smartid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class PaymentActivity extends AppCompatActivity {
    Button payBack;
    String navNameVal, navRoleval, navRFID,extraID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        navNameVal = getIntent().getStringExtra("name");
        navRoleval = getIntent().getStringExtra("role");
        navRFID = getIntent().getStringExtra("rfid");
        extraID = getIntent().getStringExtra("id");

        payBack = findViewById(R.id.payBack);

        payBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(PaymentActivity.this, DashboardActivity.class);
                back.putExtra("name",navNameVal);
                back.putExtra("role",navRoleval);
                back.putExtra("rfid",navRFID);
                back.putExtra("id",extraID);
                startActivity(back);
            }
        });
    }
}
