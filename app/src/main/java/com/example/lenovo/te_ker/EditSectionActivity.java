package com.example.lenovo.te_ker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class EditSectionActivity extends AppCompatActivity {

    EditText editTextEditSectionName, editTextEditSubjectName, editTextEditStartTime, editTextEditEndTime, editTextEditRoomName;
    Button btnEditSection;
    RadioButton radioButtonActive, radioButtonInactive;
    String section_id, name, subject, start_time, end_time, room, status, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_section);
        initExtras();
        initViews();
        initEvents();
    }

    private void initEvents() {
        editTextEditSectionName.setText(name);
        editTextEditSubjectName.setText(subject);
        editTextEditStartTime.setText(start_time);
        editTextEditEndTime.setText(end_time);
        editTextEditRoomName.setText(room);
        if(status == "0") {
            radioButtonInactive.setChecked(true);
        } else {
            radioButtonActive.setChecked(true);
        }
        url = "https://te-ker.000webhostapp.com/api/v1/edit-section";
        btnEditSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name1 = editTextEditSectionName.getText().toString();
                final String subject1 = editTextEditSubjectName.getText().toString();
                final String start_time1 = editTextEditStartTime.getText().toString();
                final String end_time1 = editTextEditEndTime.getText().toString();
                final String room1 = editTextEditRoomName.getText().toString();
                final String status1;
                if(radioButtonActive.isChecked()) {
                    status1 = "1";
                } else {
                    status1 = "0";
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String message = jsonObject.getString("message");
                            String code = jsonObject.getString("code");
                            Toast.makeText(EditSectionActivity.this, message, Toast.LENGTH_LONG).show();
                            if(code.equals("200")) {
                                Intent intent = new Intent(EditSectionActivity.this, HomeActivity.class);
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
                        params.put("section_id", section_id);
                        params.put("name", name1);
                        params.put("subject", subject1);
                        params.put("start_time", start_time1);
                        params.put("end_time", end_time1);
                        params.put("room", room1);
                        params.put("status", status1);
                        return params;
                    }
                };
                MySingleton.getInstance(EditSectionActivity.this).addToRequestQueue(stringRequest);
            }
        });
    }

    private void initExtras() {
        Intent intent = getIntent();
        section_id = intent.getStringExtra("section_id");
        name = intent.getStringExtra("name");
        subject = intent.getStringExtra("subject");
        start_time = intent.getStringExtra("start_time");
        end_time = intent.getStringExtra("end_time");
        room = intent.getStringExtra("room");
        status = intent.getStringExtra("status");
    }

    private void initViews() {
        editTextEditSectionName = findViewById(R.id.editTextEditSectionName);
        editTextEditSubjectName = findViewById(R.id.editTextEditSubjectName);
        editTextEditStartTime = findViewById(R.id.editTextEditStartTime);
        editTextEditEndTime = findViewById(R.id.editTextEditEndTime);
        editTextEditRoomName = findViewById(R.id.editTextEditRoomName);
        btnEditSection = findViewById(R.id.btnEditSection);
        radioButtonActive = findViewById(R.id.radioButtonActive);
        radioButtonInactive = findViewById(R.id.radioButtonInactive);
    }
}
