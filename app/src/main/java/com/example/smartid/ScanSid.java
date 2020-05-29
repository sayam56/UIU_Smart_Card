package com.example.smartid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class ScanSid extends AppCompatActivity {
    EditText scanSID;
    Button searchBTN;
    String scanSIDVAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_sid);

        scanSID = findViewById(R.id.scanSID);
        searchBTN = findViewById(R.id.searchBTN);

        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanSIDVAL = scanSID.getText().toString();
                Intent idReg = new Intent(ScanSid.this, LoginActivity.class);
                idReg.putExtra("sid", scanSIDVAL);
                startActivity(idReg);
            }
        });


    }
}
