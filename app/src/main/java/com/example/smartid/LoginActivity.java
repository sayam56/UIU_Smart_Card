package com.example.smartid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
Button submitBtn;
EditText etId,etEmail,etPass,etName;
TextView tvUID,tvRole;
String name,role,sid,uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        submitBtn=findViewById(R.id.submitBtn);
        etId = (EditText) findViewById(R.id.etID);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        etName = (EditText) findViewById(R.id.etName);
        tvUID = (TextView) findViewById(R.id.tvUID);
        tvRole = (TextView) findViewById(R.id.tvRole);

        final String etUIDval;
        final String etSIDval;
        uid = getIntent().getStringExtra("uid"); //here etUID val contains UID from registerMethod page
        sid = getIntent().getStringExtra("sid"); //here etSID val contains SID from ScanSid page

        if (TextUtils.isEmpty(sid)){
            //mane Student Id didnt pass through so, UID is available

            Log.d("TAG"," "+uid);
            tvUID.setText(uid);
            Log.d("inside", "inside oncreate UID");
            fetch_UID();

        }
        else{
            Log.d("sid"," "+sid);
            etId.setText(sid);
            Log.d("inside", "inside oncreate SID");
            fetch_SID();
        }





        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etPassVal;

                Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);

                etPassVal = etPass.getText().toString();

                intent.putExtra("name",name);
                intent.putExtra("role",role);
                intent.putExtra("id",sid);
                intent.putExtra("rfid",uid);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });





    }


    //write the code to connect db here and insert the test data, then view the text in the next page(dashboard)

    public void fetch_UID(){

        Log.d("inside", "inside search UID");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://ea4130c6.ngrok.io/smart_id/api_uid.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String success = jsonObject.getString("success");
                            //String role = jsonObject.getString("role");
                            name = jsonObject.getString("name");
                            sid = jsonObject.getString("id");
                            String email = jsonObject.getString("email");
                            String pass = jsonObject.getString("pass");
                            role = jsonObject.getString("role");
                           /* "+name+" "+id+" "+email*/


                            if (success.equals("1"))
                            {
                                etName.setText(name);
                                etId.setText(sid);
                                etEmail.setText(email);
                                etPass.setText(pass);
                                tvRole.setText(role);

                                Toast.makeText(getApplicationContext(),"Data Fetched",Toast.LENGTH_SHORT).show();
                                Log.d("inside"," json val : "+name+" "+sid+" "+email+" "+pass+" "+role);
                                Toast.makeText(getApplicationContext(),"YOU CAN EDIT PASSWORD HERE",Toast.LENGTH_LONG).show();
                            }else
                            {
                                Toast.makeText(getApplicationContext(),"Could Not Fetch Data",Toast.LENGTH_LONG).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //overrride form here
                Map<String, String> params = new HashMap<>();
               params.put("r_tag",tvUID.getText().toString());
                return  params;

            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }




    public void fetch_SID(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://ea4130c6.ngrok.io/smart_id/api_sid.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("sid","inside response sid");
                            String success = jsonObject.getString("success");
                            //String role = jsonObject.getString("role");
                            name = jsonObject.getString("name");
                            uid = jsonObject.getString("uid");
                            String email = jsonObject.getString("email");
                            String pass = jsonObject.getString("pass");
                            role = jsonObject.getString("role");
                            /* "+name+" "+id+" "+email*/


                            if (success.equals("1"))
                            {
                                etName.setText(name);
                                tvUID.setText(uid);
                                etEmail.setText(email);
                                etPass.setText(pass);
                                tvRole.setText(role);

                                Toast.makeText(getApplicationContext(),"Data Fetched",Toast.LENGTH_SHORT).show();
                                Log.d("inside"," json val : "+name+" "+uid+" "+email+" "+pass+" "+role);
                                Toast.makeText(getApplicationContext(),"YOU CAN EDIT PASSWORD HERE",Toast.LENGTH_LONG).show();
                            }else
                            {
                                Toast.makeText(getApplicationContext(),"Could Not Fetch Data",Toast.LENGTH_LONG).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //overrride form here
                Map<String, String> params = new HashMap<>();
                params.put("r_sid",etId.getText().toString());
                return  params;

            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }


}
