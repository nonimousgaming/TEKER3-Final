package com.example.lenovo.te_ker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lenovo.te_ker.data.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddSectionActivity extends AppCompatActivity {
    EditText editTextSectionName, editTextSubjectName, editTextStartTime, editTextEndTime, editTextRoomName;
    Button btnAddSection;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_section);
        initViews();
        initEvents();
    }

    private void initEvents() {
        url = "https://te-ker.000webhostapp.com/api/v1/create-section";
        btnAddSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user_id = AppPreference.getUserId(AddSectionActivity.this);
                final String name = editTextSectionName.getText().toString();
                final String subject = editTextSubjectName.getText().toString();
                final String start_time = editTextStartTime.getText().toString();
                final String end_time = editTextEndTime.getText().toString();
                final String room = editTextRoomName.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String message = jsonObject.getString("message");
                            String code = jsonObject.getString("code");
                            Toast.makeText(AddSectionActivity.this, message, Toast.LENGTH_LONG).show();
                            if(code.equals("200")) {
                                Intent intent = new Intent(AddSectionActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("user_id", user_id);
                        params.put("name", name);
                        params.put("subject", subject);
                        params.put("start_time", start_time);
                        params.put("end_time", end_time);
                        params.put("room", room);
                        return params;
                    }
                };
                MySingleton.getInstance(AddSectionActivity.this).addToRequestQueue(stringRequest);
            }
        });
    }

    private void initViews() {
        editTextSectionName = findViewById(R.id.editTextSectionName);
        editTextSubjectName = findViewById(R.id.editTextSubjectName);
        editTextStartTime = findViewById(R.id.editTextStartTime);
        editTextEndTime = findViewById(R.id.editTextEndTime);
        editTextRoomName = findViewById(R.id.editTextRoomName);
        btnAddSection = findViewById(R.id.btnAddSection);
    }
}
