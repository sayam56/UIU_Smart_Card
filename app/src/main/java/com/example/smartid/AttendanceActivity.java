package com.example.smartid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity {

    private static final String url = "https://ea4130c6.ngrok.io/smart_id/course.php";

    Button attBack;
    String navNameVal, navRoleval, navRFID;
    public static String extraID;
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;

    List<Course> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        list = new ArrayList<>();


        recyclerView = findViewById(R.id.recView);


        navNameVal = getIntent().getStringExtra("name");
        navRoleval = getIntent().getStringExtra("role");
        navRFID = getIntent().getStringExtra("rfid");
        extraID = getIntent().getStringExtra("id");

        attBack = findViewById(R.id.attBack);


      /*  recyclerView.setLayoutManager(new LinearLayoutManager(this));*/


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration( this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //add the list values

        loadCourse();



        attBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(AttendanceActivity.this, DashboardActivity.class);
                back.putExtra("name",navNameVal);
                back.putExtra("role",navRoleval);
                back.putExtra("rfid",navRFID);
                back.putExtra("id",extraID);
                startActivity(back);
            }
        });
    }

    private void loadCourse(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray course = new JSONArray(response);

                    for (int i=0; i<course.length(); i++){
                        JSONObject courseObject = course.getJSONObject(i);

                        String c_name = courseObject.getString("c_name");
                        String section= courseObject.getString("section");

                        //Log.d("kktag", "onResponse: "+c_name+" "+section);

                        Course course1 = new Course(c_name,section);
                        list.add(course1);

                    } //for ends

                    recyclerAdapter = new RecyclerAdapter( AttendanceActivity.this, list);
                    recyclerView.setAdapter(recyclerAdapter);

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
                params.put("s_id",extraID);
                return  params;

            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
