package com.example.smartid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
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

public class AttendanceUpdateActivity extends AppCompatActivity {

    private static final String url = "https://ea4130c6.ngrok.io/smart_id/attendance.php";

    String c_name,sec_name,s_id;
    TextView cnameTV;
    RecyclerAdapterAttendance recyclerAdapterAttendance;
    RecyclerView recyclerView1;
    List<Attendance> attList;
    TextView prTV,abTV;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_update);

        attList = new ArrayList<>();
        cnameTV = findViewById(R.id.attUpdate);

        recyclerView1 = findViewById(R.id.recViewAtt);
        prTV = findViewById(R.id.prTV);
        abTV = findViewById(R.id.abTV);

        //////////////////////////////////////////////////////////////////////////////////////

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView1.addItemDecoration(dividerItemDecoration);

        if (getIntent().hasExtra("c_name")){
            c_name = getIntent().getStringExtra("c_name");
            sec_name = getIntent().getStringExtra("sec_name");
            s_id = getIntent().getStringExtra("s_id");
        }

        cnameTV.setText(c_name);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAttendance();

                recyclerAdapterAttendance.notifyDataSetChanged();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

        loadAttendance();
    }


    private void loadAttendance(){
        swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                swipeRefreshLayout.setRefreshing(false);
                try {
                    JSONArray attendance = new JSONArray(response);
                    attList.clear();
                    for (int i=0; i<attendance.length(); i++){
                        JSONObject attendanceJSONObject = attendance.getJSONObject(i);

                        String date = attendanceJSONObject.getString("date");
                        String present= attendanceJSONObject.getString("present");
                        String absent= attendanceJSONObject.getString("absent");


                        //Log.d("kktag", "onResponse: "+c_name+" "+section);

                        Attendance attendance1 = new Attendance(date,present,absent);
                        attList.add(attendance1);

                    } //for ends

                    recyclerAdapterAttendance = new RecyclerAdapterAttendance(attList,AttendanceUpdateActivity.this);
                    recyclerView1.setAdapter(recyclerAdapterAttendance);


                    Attendance attendance2 = attList.get(RecyclerAdapterAttendance.pos);
                    prTV.setText(attendance2.getPresent());
                    abTV.setText(attendance2.getAbsent());

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
                params.put("sec_name",sec_name);
                params.put("s_id",s_id);
                return  params;

            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }
}
