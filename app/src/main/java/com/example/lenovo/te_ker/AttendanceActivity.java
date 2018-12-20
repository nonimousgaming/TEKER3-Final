package com.example.lenovo.te_ker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lenovo.te_ker.data.Attendance;
import com.example.lenovo.te_ker.data.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity {

    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Attendance> movieList;
    private RecyclerView.Adapter adapter;
    String section_id;
    TextView textViewNoAttendance;
    Button btnPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Intent intent = getIntent();
        section_id = intent.getStringExtra("section_id");
        textViewNoAttendance = findViewById(R.id.textViewNoAttendance);
        btnPDF = findViewById(R.id.btnPDF);
        mList = findViewById(R.id.attendance_list);
        mList.setVisibility(View.VISIBLE);
        movieList = new ArrayList<>();
        adapter = new AttendanceAdapater(getApplicationContext(),movieList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url = "https://te-ker.000webhostapp.com/api/v1/get-attendance";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(response.contentEquals("[]")) {
                        textViewNoAttendance.setVisibility(View.VISIBLE);
                        Toast.makeText(AttendanceActivity.this, "No attendance uploaded yet", Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Attendance attendance = new Attendance();
                            attendance.setID(jsonObject.getString("id"));
                            attendance.setName(jsonObject.getString("name"));
                            attendance.setAttendance(jsonObject.getString("attendance"));
                            attendance.setTotal(jsonObject.getString("total"));
                            attendance.setPhone(jsonObject.getString("phone"));
                            movieList.add(attendance);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AttendanceActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("section_id", section_id);
                return params;
            }
        };
        MySingleton.getInstance(AttendanceActivity.this).addToRequestQueue(stringRequest);

        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://te-ker.000webhostapp.com/api/v1/export-pdf";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String url = jsonObject.getString("url");
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AttendanceActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("section_id", section_id);
                        return params;
                    }
                };
                MySingleton.getInstance(AttendanceActivity.this).addToRequestQueue(stringRequest);
            }
        });
    }




}
