package com.example.smartid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {
    private static final String url = "https://e624297dc64c.ngrok.io/smart_id/paymentAPI.php";


    /* Button payBack;*/
    String navNameVal, navRoleval, navRFID, extraID, balance_json, dept, batch;
    TextView balance;
    RecyclerAdapterPayment recyclerAdapterPayment;
    RecyclerView recyclerView2;
    List<Payment> payList;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        navNameVal = getIntent().getStringExtra("name");
        navRoleval = getIntent().getStringExtra("role");
        navRFID = getIntent().getStringExtra("rfid");
        extraID = getIntent().getStringExtra("id");
        dept = getIntent().getStringExtra("dept");
        batch = getIntent().getStringExtra("batch");

        payList = new ArrayList<>();
        balance = findViewById(R.id.amount); //amount=balance
        recyclerView2 = findViewById(R.id.recViewPay);


        //////////////////////////////////////////////////////////////////////////////////////

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView2.addItemDecoration(dividerItemDecoration);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshPay);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                 loadBalance();
                loadPayment();

                recyclerAdapterPayment.notifyDataSetChanged();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

         loadBalance();
         loadPayment();

    }

    private void loadBalance(){
        swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://e624297dc64c.ngrok.io/smart_id/balanceAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String success = jsonObject.getString("success");
                            //String role = jsonObject.getString("role");
                            balance_json = jsonObject.getString("balance");

                            //Log.d("balance", "success is: "+success);

                            if (success.equals("1"))
                            {
                                balance.setText("Balance: "+balance_json+" TK");

                            }else
                            {
                                Toast.makeText(getApplicationContext(),"Could Not Load Balance",Toast.LENGTH_LONG).show();
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
                params.put("rfid",navRFID);
                return  params;

            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    private void loadPayment(){
        swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                swipeRefreshLayout.setRefreshing(false);
                try {
                    JSONArray payment = new JSONArray(response);
                    payList.clear();
                    for (int i=0; i<payment.length(); i++){
                        JSONObject attendanceJSONObject = payment.getJSONObject(i);

                        String date = attendanceJSONObject.getString("tr_id");
                        String vendor= attendanceJSONObject.getString("vendor");
                        String amount= attendanceJSONObject.getString("amount");


                        //Log.d("kktag", "onResponse: "+c_name+" "+section);

                        Payment payment1 = new Payment("Transaction ID: "+date,"Vendor: "+vendor,"Paid: "+amount+" TK");
                        payList.add(payment1);

                    } //for ends

                    recyclerAdapterPayment = new RecyclerAdapterPayment(payList, PaymentActivity.this);
                    recyclerView2.setAdapter(recyclerAdapterPayment);

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
                params.put("rfid",navRFID);

                return  params;

            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }
}
