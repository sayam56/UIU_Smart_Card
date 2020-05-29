package com.example.smartid;

import androidx.appcompat.app.AppCompatActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.math.BigInteger;

public class RegisterMethod extends AppCompatActivity {
Button cardBtn, idBtn;
    String data;
    NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_method);
        cardBtn = findViewById(R.id.cardBtn);
        idBtn = findViewById(R.id.idBTN);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter != null && nfcAdapter.isEnabled()) {
            Toast.makeText(this, "WITH CARD RECOMMENDED", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "NFC NOT AVAILABLE", Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(RegisterMethod.this, "WITH ID RECOMMENDED", Toast.LENGTH_LONG).show();
                }
            }, 2000);
        }




        idBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent scan = new Intent(RegisterMethod.this, ScanSid.class);
            startActivity(scan);

            }
        });

        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.scan_popup, null);


                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = false; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, -300);



                //write the UID getter code here and pass the val in intent
                // readTag(getIntent());
                //UID read on new intent received



                //click listener for the pop up view this will later be converted to when you find the UID from the card
               /* popupView.setOnTouchListener(new View.OnTouchListener(){

                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {


                        return false;
                    }
                });*/


            }
        });





    }

     /*   public void readTag(Intent intent)
    {
        //tagIntent.setText(intent.getAction());


    }

    //To display the UID
    static String bin2hex(byte[] data) {

    }*/

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra(NfcAdapter.EXTRA_TAG)) {

            Toast.makeText(this, "NFC intent Received!",
                    Toast.LENGTH_LONG).show();
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            data = String.format("%0" + (tag.getId().length * 2) + "X", new BigInteger(1,tag.getId()));
            Log.d("TAG", "tag ID from REGISTER MTHOD = "+ data);

            Intent home=new Intent(RegisterMethod.this,LoginActivity.class);
            home.putExtra("uid",data);

            startActivity(home);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        enableForegroundDispatchSystem();
    }

    @Override
    protected void onPause() {
        super.onPause();

        disableForegroundDispatchSystem();

    }

    private void enableForegroundDispatchSystem() {
        Intent intent = new Intent(this, RegisterMethod.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilter = new IntentFilter[]{};


        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);

    }


    private void disableForegroundDispatchSystem() {
        nfcAdapter.disableForegroundDispatch(this);

    }


}
